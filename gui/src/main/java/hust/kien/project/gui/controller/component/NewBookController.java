package hust.kien.project.gui.controller.component;

import hust.kien.project.core.book.BookCreationRequest;
import hust.kien.project.core.book.BookService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.LocalDate;
import java.util.List;

import static hust.kien.project.gui.controller.component.BookComponentUtils.*;

public class NewBookController {

    private static final Logger log = LoggerFactory.getLogger(NewBookController.class);

    @Autowired
    private BookService librarianService;

    @FXML
    private TextArea bookNameField;

    @FXML
    private TextField stockField;

    @FXML
    private TextField moneyField;

    @FXML
    private TextField yearField;

    public void initialize() {
        // Do nothing?
    }

    @FXML
    private void handleAddNewBook() {
        if (!isIntegerValid(yearField.getText()) || !isIntegerValid(stockField.getText())
                || !isDoubleValid(moneyField.getText()) || bookNameField.getText().isBlank()) {
            handleValidation();
            return;
        }

        int year = Integer.parseInt(yearField.getText());

        var book = new BookCreationRequest(
                bookNameField.getText(),
                LocalDate.of(year, 1, 1),
                List.of(),
                List.of());

        log.info("Adding new book: {}", book);
        librarianService.create(book);

        Window window = stockField.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }

    @FXML
    private void handleValidation() {
        setElementBorderFromValidationResult(yearField, isIntegerValid(yearField.getText()));
        setElementBorderFromValidationResult(stockField, isIntegerValid(stockField.getText()));
        setElementBorderFromValidationResult(moneyField, isDoubleValid(moneyField.getText()));
        setElementBorderFromValidationResult(bookNameField, !bookNameField.getText().isBlank());
    }
}
