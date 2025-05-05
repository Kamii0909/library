package hust.kien.project.gui.pages.main.components.authors;

import java.io.IOException;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.BeanFactoryAnnotationUtils;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import hust.kien.project.core.model.author.Author;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.gui.controller.utils.FxUtils;
import hust.kien.project.gui.pages.FxmlBinding;
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
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

class Fxml extends FxmlBinding<@NonNull State, @NonNull Interactions> implements Interactions {
    
    private final ApplicationContext context;
    
    private final ObjectProvider<Region> authorComponentRegionProvider;
    
    private final Image defaultAuthorImage;
    
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
        this.authorComponentRegionProvider = new ObjectProvider<Region>() {
            
            @Override
            public Region getObject() throws BeansException {
                throw new UnsupportedOperationException("Unimplemented method 'getObject'");
            }
            
            @Override
            public Region getObject(Object... args) throws BeansException {
                return (Region) context.getBean("authorComponentRegion", args);
            }
            
            @Override
            public Region getIfAvailable() throws BeansException {
                throw new UnsupportedOperationException("Unimplemented method 'getIfAvailable'");
            }
            
            @Override
            public Region getIfUnique() throws BeansException {
                throw new UnsupportedOperationException("Unimplemented method 'getIfUnique'");
            }
        };
        
        Resource imageResource = BeanFactoryAnnotationUtils
                .qualifiedBeanOfType(context,
                        Resource.class,
                        "defaultBookImage");
        
        try {
            this.defaultAuthorImage = FxUtils.renderImage(new Image(imageResource.getInputStream(),
                    0, 0, true, true), 1);
        } catch (IOException e) {
            throw new AssertionError(e);
        }
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
        return authorComponentRegionProvider.getObject(author, defaultAuthorImage);
    }
    
    private String validationStyles(boolean isValid) {
        return isValid ? "-fx-boder-color: none" : "-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5";
    }
    
    @Override
    public AuthorsController createController() {
        return new AuthorsController(bind(), context.getBean(LibrarianService.class));
    }
}
