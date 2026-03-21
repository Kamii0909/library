package hust.kien.project.gui.pages.main.components.authors;

import hust.kien.project.core.author.AuthorCreationRequest;
import hust.kien.project.core.author.AuthorFilter;
import hust.kien.project.core.author.ReadonlyAuthor;
import hust.kien.project.gui.pages.ImmutableNode;
import hust.kien.project.gui.view.event.AuthorDeletedEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener;
import javafx.collections.ObservableList;
import javafx.collections.ObservableMap;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Node;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import hust.kien.project.core.author.AuthorService;
import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.InterfaceComponent;
import hust.kien.project.gui.pages.main.components.authors.components.author.AuthorComponentFactory;

import java.time.LocalDate;
import java.util.List;

import static hust.kien.project.gui.controller.component.BookComponentUtils.isIntegerValid;
import static hust.kien.project.gui.pages.Namespace.*;

public class AuthorsComponent implements InterfaceComponent {
    private static final Logger log = LoggerFactory.getLogger(AuthorsComponent.class);

    private final AuthorService authorService;
    private final AuthorComponentFactory factory;
    private final ImmutableNode region;

    private final StringProperty name;
    private final StringProperty age;
    private final ObservableList<ReadonlyAuthor> authors;

    private final BooleanProperty isNameValid;
    private final BooleanProperty isAgeValid;

    private final VBox authorContainer;

    @SuppressWarnings("null")
    AuthorsComponent(
            @Value("classpath:gui/manage_author_new.fxml") Resource fxml,
            ResourceHelper helper,
            AuthorService authorService,
            AuthorComponentFactory factory) {
        this.authorService = authorService;
        this.factory = factory;

        FXMLLoader loader = helper.load(fxml, this);
        this.region = new ImmutableNode(loader.getRoot());

        this.isNameValid = new SimpleBooleanProperty(true);
        this.isAgeValid = new SimpleBooleanProperty(true);

        this.authors = FXCollections.observableArrayList();

        ObservableMap<String, Object> namespace = loader.getNamespace();

        TextField nameField = textField(namespace, "nameField");
        TextField ageField = textField(namespace, "ageField");

        // TODO: use TextFormatter to ensure it is a valid number instead.

        this.name = nameField.textProperty();
        this.age = ageField.textProperty();

        bindStyleToValidationResult(isNameValid, nameField);
        bindStyleToValidationResult(isAgeValid, ageField);

        this.authorContainer = vBox(namespace, "authorContainer");

        bindAuthorListToAuthorComponents();
    }


    @Override
    public ImmutableNode element() {
        return region;
    }

    private void bindAuthorListToAuthorComponents() {

        authors.addListener((ListChangeListener.Change<? extends ReadonlyAuthor> change) -> {
            change.reset();
            while (change.next()) {
                if (change.wasPermutated()) {
                    throw new UnsupportedOperationException("not implemented");
                }
                if (change.wasAdded()) {
                    authorContainer
                            .getChildren()
                            .addAll(change.getFrom(),
                                    change.getAddedSubList().stream().map(this::map).toList());
                }
                if (change.wasRemoved()) {
                    authorContainer
                            .getChildren()
                            .remove(change.getFrom(), change.getFrom() + change.getRemovedSize());
                }
            }
        });

        authorContainer.addEventFilter(AuthorDeletedEvent.AUTHOR_DELETED_EVENT, ev -> {
            ev.consume();
            authors.remove(ev.getDeletedAuthor());
        });
    }


    private static void bindStyleToValidationResult(BooleanProperty isValid, TextField field) {
        ObservableValue<String> styles = isValid.map(AuthorsComponent::validationStyles);
        field.styleProperty().bind(styles);
    }

    private static String validationStyles(boolean isValid) {
        return isValid ? "-fx-boder-color: none" : "-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5";
    }

    private Node map(ReadonlyAuthor author) {
        return factory.createComponent(author).element().getValue();
    }


    @FXML
    public void handleFilter() {
        String name = this.name.get();
        String age = this.age.get();

        log.debug("Filter request name: {} age: {} ", name, age);

        isNameValid.set(true);
        if (!age.isBlank()) {
            boolean ageValid = isIntegerValid(age);
            isAgeValid.set(ageValid);
            if (!ageValid)
                return;
        }

        AuthorFilter builder = new AuthorFilter();

        if (!name.isEmpty()) {
            builder.nameContains(name);
        }
        if (!age.isBlank()) {
            int i = Integer.parseInt(age);
            builder.ageBetween(i, i);
        }

        List<ReadonlyAuthor> authors = authorService.find(builder);

        this.authors.setAll(authors);
    }

    @FXML
    public void handleAddAuthor() {

        StringProperty nameProp = this.name;
        StringProperty ageProp = this.age;

        String name = nameProp.get();
        String age = ageProp.get();

        boolean isNameValid = !name.isBlank();
        boolean isAgeValid = isValidInteger(age);

        this.isNameValid.set(isNameValid);
        this.isAgeValid.set(isAgeValid);

        if (!isAgeValid || !isNameValid)
            return;

        AuthorCreationRequest author = new AuthorCreationRequest(name, LocalDate.now(), List.of());

        log.info("Adding author {}", author);
        authorService.create(author);

        nameProp.set("");
        ageProp.set("");

        this.authors.setAll(authorService.find(new AuthorFilter()));
    }

    private static boolean isValidInteger(String year) {
        try {
            Integer.parseInt(year);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
