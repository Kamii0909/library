package hust.kien.project.gui.pages.main.components.authors;

import static hust.kien.project.gui.controller.component.BookComponentUtils.isIntegerValid;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.AuthorFilter;
import hust.kien.project.core.author.AuthorService;
import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.FxmlComponent;
import hust.kien.project.gui.pages.main.components.authors.components.author.AuthorComponentFactory;
import hust.kien.project.gui.view.event.AuthorDeletedEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class AuthorsComponent extends FxmlComponent<@NonNull AuthorsComponent> {

    private static final Logger log = LoggerFactory.getLogger(AuthorsComponent.class);

    private @FXML TextField nameField;
    private @FXML TextField ageField;
    private @FXML VBox authorContainer;

    private State state;

    private final AuthorService authorService;

    private final AuthorComponentFactory factory;

    @SuppressWarnings("null")
    AuthorsComponent(@Value("classpath:gui/manage_author_new.fxml") Resource fxml,
            ResourceHelper helper,
            AuthorService authorService,
            AuthorComponentFactory factory) {
        super(fxml, helper);
        this.authorService = authorService;
        this.factory = factory;
    }

    @FXML
    public void initialize() {
        this.state = initializeState();
    }

    private State initializeState() {

        BooleanProperty validName = new SimpleBooleanProperty(true);
        BooleanProperty validAge = new SimpleBooleanProperty(true);

        StringBinding nameStyles = Bindings.createStringBinding(() -> validationStyles(validName.get()), validName);
        StringBinding ageStyles = Bindings.createStringBinding(() -> validationStyles(validAge.get()), validAge);

        nameField.styleProperty().bind(nameStyles);
        ageField.styleProperty().bind(ageStyles);

        ObservableList<Author> authors = FXCollections.observableArrayList();

        authors.addListener((Change<? extends Author> change) -> {
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

        return new State(nameField.textProperty(), ageField.textProperty(), authors, validName, validAge);
    }

    @FXML
    public void handleFilter() {
        String name = state.name().get();
        String age = state.age().get();

        BooleanProperty isNameValid = state.validName();
        BooleanProperty isAgeValid = state.validAge();

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

        List<@NonNull Author> authors = authorService.find(builder);

        state.authors().setAll(authors);
    }

    @FXML
    public void handleAddAuthor() {

        StringProperty nameProp = state.name();
        StringProperty ageProp = state.age();

        String name = nameProp.get();
        String age = ageProp.get();

        boolean isNameValid = !name.isBlank();
        boolean isAgeValid = isValidInteger(age);

        state.validName().set(isNameValid);
        state.validAge().set(isAgeValid);

        if (!isAgeValid || !isNameValid)
            return;

        Author author = new Author(name, Integer.parseInt(age));

        log.info("Adding author {}", author);
        authorService.create(author);

        nameProp.set("");
        ageProp.set("");

        state.authors().setAll(authorService.find(new AuthorFilter()));
    }

    private Region map(Author author) {
        return factory.createComponent(author).element();
    }

    private String validationStyles(boolean isValid) {
        return isValid ? "-fx-boder-color: none" : "-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5";
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
