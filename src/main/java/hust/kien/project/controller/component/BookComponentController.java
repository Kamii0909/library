package hust.kien.project.controller.component;

import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.image.ImageView;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookComponentController {

    @FXML
    private Text stock;

    @FXML
    private TextField authorName;

    @FXML
    private TextArea bookName;

    @FXML
    private ImageView bookImage;

    private final Book book;

    private final LibrarianService librarianService;

    public BookComponentController(Book book, LibrarianService librarianService) {
        this.book = book;
        this.librarianService = librarianService;
    }

    private void initialize() {
        bookName.setText(book.getBookInfo().getName());
        authorName.setText(book.getBookInfo().getAuthors().stream().findAny().orElseThrow()
            .getAuthorInfo().getName());
        stock.setText("Ton kho: " + book.getBookStock().getStock());
    }

    @FXML
    private void handleClick(ActionEvent event) {
        log.info("Book: {{}}", book);

        authorName.setText("Changed Woohoo");

    }
}
