package hust.kien.project.gui.pages.main.components.authors;

import hust.kien.project.core.author.Author;
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

class Fxml {
    private @FXML TextField nameField;
    private @FXML TextField ageField;
    private @FXML VBox authorContainer;

    private final AuthorsComponent hook;
    private final AuthorComponentFactory factory;

    @SuppressWarnings("null")
    Fxml(AuthorsComponent hook, AuthorComponentFactory factory) {
        this.hook = hook;
        this.factory = factory;
    }

    @FXML
    public void initialize() {
        hook.setState(initializeState());
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

    private String validationStyles(boolean isValid) {
        return isValid ? "-fx-boder-color: none" : "-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5";
    }

    private Region map(Author author) {
        return factory.createComponent(author).element();
    }
}
