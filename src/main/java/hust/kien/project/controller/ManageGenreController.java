package hust.kien.project.controller;

import static hust.kien.project.controller.component.BookComponentUtils.setElementBorderFromValidationResult;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Component;
import hust.kien.project.controller.utils.AlertUtils;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.BookGenreSpecificationBuilder;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManageGenreController {

    @FXML
    private TextField genreFilter;

    @FXML
    private ListView<BookGenre> genreContainer;

    private final LibrarianService librarianService;

    public ManageGenreController(LibrarianService librarianService) {
        this.librarianService = librarianService;
    }

    public void initialize() {

        doUpdateUI(librarianService.dynamicFind(new BookGenreSpecificationBuilder()));

        genreContainer.setCellFactory(param -> {
            ListCell<BookGenre> cell = new ListCell<>() {
                @Override
                public void updateItem(BookGenre genre, boolean empty) {
                    super.updateItem(genre, empty);
                    if (empty || genre == null) {
                        setText(null);
                    } else {
                        setText(genre.getName());
                    }
                }
            };

            cell.getStyleClass().add("alatsi");
            cell.setStyle("-fx-font-size: 12;");

            MenuItem menuItem = new MenuItem("Xoa");
            menuItem.setOnAction(ev -> deleteBookGenre());
            cell.setContextMenu(new ContextMenu(menuItem));

            return cell;
        });
    }

    private void deleteBookGenre() {
        BookGenre bookGenre = genreContainer.getSelectionModel().getSelectedItem();

        Optional<ButtonType> confirmation = AlertUtils.showAndWaitOkCancelAlert(
            "Ban co chac muon xoa the loai " + bookGenre.getName() + " hay khong?");

        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            log.info("Deleting book genre: {}", bookGenre);
            librarianService.delete(bookGenre);

            genreContainer.getItems().remove(genreContainer.getSelectionModel().getSelectedIndex());
        }
    }

    @FXML
    private void handleFilterGenre() {
        log.debug("Filter genre request: {}", genreFilter.getText());

        var builder = new BookGenreSpecificationBuilder();
        if (!genreFilter.getText().isBlank()) {
            builder.nameLike(genreFilter.getText());
        }
        doUpdateUI(librarianService.dynamicFind(builder));
    }

    @FXML
    private void handleAddGenre() {
        setElementBorderFromValidationResult(genreFilter, !genreFilter.getText().isBlank());

        if (genreFilter.getText().isBlank()) {
            return;
        }

        log.info("Adding genre {}", genreFilter.getText());
        BookGenre bookGenre = new BookGenre(genreFilter.getText());
        bookGenre = librarianService.saveOrUpdate(bookGenre);
        genreContainer.getItems().add(bookGenre);
    }

    private void doUpdateUI(List<BookGenre> bookGenres) {
        if (log.isTraceEnabled()) {
            log.trace("Updating UI with {}", bookGenres);
        }
        genreContainer.setItems(FXCollections.observableList(bookGenres));
    }
}
