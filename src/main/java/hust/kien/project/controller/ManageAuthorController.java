package hust.kien.project.controller;

import static hust.kien.project.controller.component.BookComponentUtils.isIntegerValid;
import static hust.kien.project.controller.component.BookComponentUtils.setElementBorderFromValidationResult;

import java.io.IOException;
import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;

import hust.kien.project.controller.utils.FxUtils;
import hust.kien.project.model.author.Author;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.AuthorSpecificationBuilder;
import hust.kien.project.view.event.AuthorDeletedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;

public class ManageAuthorController {
    private static final Logger log = LoggerFactory.getLogger(ManageAuthorController.class);
    
    @FXML
    private TextField nameField;
    
    @FXML
    private TextField ageField;
    
    @FXML
    private VBox authorContainer;
    
    private final LibrarianService librarianService;
    
    private final ObjectProvider<Region> authorComponentRegionProvider;
    
    private final Image defaultAuthorImage;
    
    public ManageAuthorController(LibrarianService librarianService,
        @Qualifier("authorComponentRegion") ObjectProvider<Region> authorComponentRegionProvider,
        @Qualifier("defaultBookImage") Resource defaultAuthorImage) throws IOException {
        this.librarianService = librarianService;
        this.authorComponentRegionProvider = authorComponentRegionProvider;
        this.defaultAuthorImage = FxUtils.renderImage(new Image(defaultAuthorImage.getInputStream(),
                0, 0, true, true), 1);
    }
    
    public void initialize() {
        refreshAll();
        authorContainer.addEventFilter(AuthorDeletedEvent.AUTHOR_DELETED_EVENT, ev -> refreshAll());
    }
    
    private void refreshAll() {
        refreshElements(librarianService.dynamicFind(new AuthorSpecificationBuilder()));
    }
    
    private void refreshElements(Collection<Author> authors) {
        authorContainer
                .getChildren()
                .setAll(authors.stream()
                        .map(a -> authorComponentRegionProvider.getObject(a, defaultAuthorImage))
                        .toList());
    }
    
    @FXML
    private void handleFilter() {
        log.debug("Filter request name: {} age: {} ", nameField.getText(), ageField.getText());
        
        if (nameField.getText().isBlank()) {
            if (ageField.getText().isBlank() || isIntegerValid(ageField.getText())) {
                setElementBorderFromValidationResult(ageField, true);
                setElementBorderFromValidationResult(nameField, true);
            } else if (!isIntegerValid(ageField.getText())) {
                handleValidation();
                return;
            }
        } else {
            setElementBorderFromValidationResult(ageField, true);
            setElementBorderFromValidationResult(nameField, true);
        }
        
        AuthorSpecificationBuilder builder = new AuthorSpecificationBuilder();
        
        if (!nameField.getText().isEmpty()) {
            builder.nameContains(nameField.getText());
        }
        if (isIntegerValid(ageField.getText())) {
            int i = Integer.parseInt(ageField.getText());
            builder.ageBetween(i, i);
        }
        
        refreshAll();
    }
    
    @FXML
    private void handleAddAuthor() {
        if (!isIntegerValid(ageField.getText()) || nameField.getText().isBlank()) {
            handleValidation();
            return;
        }
        Author author = Author.builder()
                .name(nameField.getText())
                .age(Integer.parseInt(ageField.getText()))
                .build();
        
        log.info("Adding author {}", author);
        librarianService.saveOrUpdate(author);
        
        nameField.clear();
        ageField.clear();
        
        refreshAll();
    }
    
    private void handleValidation() {
        setElementBorderFromValidationResult(ageField, isIntegerValid(ageField.getText()));
        setElementBorderFromValidationResult(nameField, !nameField.getText().isBlank());
    }
}
