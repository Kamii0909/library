package hust.kien.project.gui.controller.component;

import static hust.kien.project.gui.controller.component.BookComponentUtils.isIntegerValid;
import static hust.kien.project.gui.controller.component.BookComponentUtils.setElementBorderFromValidationResult;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.view.event.AuthorDeletedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;

public class AuthorComponentController {
    
    private static final Logger log = LoggerFactory.getLogger(AuthorComponentController.class);
    
    @FXML
    private TextField ageField;
    
    @FXML
    private TextArea nameField;
    
    @FXML
    private ToggleButton modifyButton;
    
    @FXML
    private ImageView imageView;
    
    @FXML
    private StackPane imageContainer;
    
    private Author author;
    
    private Image image;
    
    @Autowired
    private LibrarianService librarianService;
    
    public AuthorComponentController(Author author, Image image) {
        this.author = author;
        this.image = image;
    }
    
    public void initialize() {
        setNameAndAgeField(author.name(), author.age());
        
        Circle circle = new Circle(40, 35, 30);
        
        imageView.setClip(circle);
        imageView.setImage(image);
    }
    
    @FXML
    private void handleDeleteAuthor() {
        Optional<ButtonType> response = AlertUtils.showAndWaitOkCancelAlert("Are you sure?");
        
        if (response.isEmpty() || response.get() == ButtonType.CANCEL) {
            // Do nothing
        } else if (response.get() == ButtonType.OK) {
            librarianService.delete(author);
            nameField.fireEvent(new AuthorDeletedEvent(author));
            log.info("Delete author request: {{}}", author);
        } else {
            throw new IllegalStateException("can't be here, should be only OK Cancel and Close");
        }
        
    }
    
    private void toggleEditable() {
        boolean editable = modifyButton.isSelected();
        nameField.setEditable(editable);
        ageField.setEditable(editable);
    }
    
    @FXML
    private void handleModifyAuthor() {
        log.info("Modify author request {}", author);
        if (modifyButton.isSelected()) {
            modifyButton.setText("Hoan tat");
        } else {
            modifyButton.setText("Sua thong tin");
            if (validateAndRevertFields()) {
                commitAuthor();
            }
        }
        toggleEditable();
    }
    
    @FXML
    private void validate() {
        setElementBorderFromValidationResult(ageField, isIntegerValid(ageField.getText()));
        setElementBorderFromValidationResult(nameField, !nameField.getText().isBlank());
    }
    
    private boolean validateAndRevertFields() {
        if (isIntegerValid(ageField.getText()) && !nameField.getText().isBlank()) {
            author.setName(nameField.getText());
            author.setAge(Integer.parseInt(ageField.getText()));
            return true;
        } else {
            setNameAndAgeField(author.name(), author.age());
            validate();
            return false;
        }
    }
    
    private void setNameAndAgeField(String name, int age) {
        nameField.setText(name);
        ageField.setText(Integer.toString(age));
    }
    
    private void commitAuthor() {
        log.info("Saving author: {}", author);
        author = librarianService.saveOrUpdate(author);
    }
}
