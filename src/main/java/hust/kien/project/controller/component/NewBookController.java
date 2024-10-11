package hust.kien.project.controller.component;

import static hust.kien.project.controller.component.BookComponentUtils.isDoubleValid;
import static hust.kien.project.controller.component.BookComponentUtils.isIntegerValid;
import static hust.kien.project.controller.component.BookComponentUtils.setElementBorderFromValidationResult;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class NewBookController {
    
    private static final Logger log = LoggerFactory.getLogger(NewBookController.class);
    
    @Autowired
    private LibrarianService librarianService;
    
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
        
        var book = Book.builder()
                .name(bookNameField.getText())
                .releasedYear(Integer.parseInt(yearField.getText()))
                .stock(Integer.parseInt(stockField.getText()))
                .reimburseCost(Double.parseDouble(moneyField.getText().replaceAll("[,' ]", "")))
                .build();
        
        log.info("Adding new book: {}", book);
        librarianService.saveOrUpdate(book);
        
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
