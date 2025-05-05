package hust.kien.project.gui.pages.main.components.authors;

import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationContext;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.gui.pages.FxmlBinding;
import hust.kien.project.gui.pages.main.components.authors.components.author.AuthorComponentFactory;
import hust.kien.project.gui.view.event.AuthorDeletedEvent;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.StringBinding;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.collections.FXCollections;
import javafx.collections.ListChangeListener.Change;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

class Fxml extends FxmlBinding<@NonNull State, @NonNull Interactions> implements Interactions {
    
    private final ApplicationContext context;
    
    private final AuthorComponentFactory factory;
        
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField ageField;
    
    @FXML
    private VBox authorContainer;
    
    @SuppressWarnings("null")
    Fxml(AuthorsComponent authors, ApplicationContext context) {
        super(authors);
        this.context = context;
        this.factory = context.getBean(AuthorComponentFactory.class);
    }
    
    @FXML
    @Override
    public void handleFilter() {
        handler().handleFilter();
    }
    
    @FXML
    @Override
    public void handleAddAuthor() {
        handler().handleAddAuthor();
    }
    
    @Override
    public State bind() {
        
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
    
    private Region map(Author author) {
        return factory.createComponent(author).element();
    }
    
    private String validationStyles(boolean isValid) {
        return isValid ? "-fx-boder-color: none" : "-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5";
    }
    
    @Override
    public AuthorsController createController() {
        return new AuthorsController(bind(), context.getBean(LibrarianService.class));
    }
}
