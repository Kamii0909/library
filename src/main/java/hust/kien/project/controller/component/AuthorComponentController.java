package hust.kien.project.controller.component;

import java.util.Optional;
import org.springframework.beans.factory.annotation.Autowired;
import hust.kien.project.controller.utils.AlertUtils;
import hust.kien.project.model.author.Author;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.view.event.AuthorDeletedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Circle;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class AuthorComponentController {

    @FXML
    private TextField ageField;

    @FXML
    private TextArea nameField;

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
        nameField.setText(author.getAuthorInfo().getName());
        ageField.setText(Integer.toString(author.getAuthorInfo().getAge()));

        Circle circle = new Circle(40, 35,30);
        
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

    @FXML
    private void handleModifyAuthor() {
        log.info("Modify author request {}", author);
    }
}
