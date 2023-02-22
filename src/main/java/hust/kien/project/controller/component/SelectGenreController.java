package hust.kien.project.controller.component;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.controlsfx.control.ListSelectionView;
import org.springframework.beans.factory.annotation.Autowired;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.BookGenreSpecificationBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class SelectGenreController {

    @FXML
    private ListSelectionView<String> genreSelection;

    @FXML
    private TextField filterTextField;


    private final List<BookGenre> newGenres;

    private final Map<String, BookGenre> newGenreStrings;

    private final Set<BookGenre> currentGenres;

    private final LibrarianService librarianService;

    public SelectGenreController(Book book, LibrarianService librarianService) {
        this.librarianService = librarianService;

        this.currentGenres = book.getBookInfo().getBookGenres();
        log.info("{{}}", currentGenres);

        this.newGenres = librarianService
            .dynamicFind(new BookGenreSpecificationBuilder())
            .stream()
            .filter(bg -> !currentGenres.contains(bg))
            .toList();

        log.info("{{}}", newGenres);

        this.newGenreStrings = newGenres.stream()
            .collect(Collectors.toMap(BookGenre::getName, Function.identity()));
    }

    public void initialize() {
        genreSelection.getSourceItems().addAll(newGenreStrings.keySet());
    }

    @FXML
    private void handleFilterByName() {
        log.info("Handle filter by name: {}", filterTextField.getText());
    }

    @FXML
    private void handleCompleted() {

        log.info("{{}}", currentGenres);

        currentGenres.addAll(genreSelection.getTargetItems()
            .stream()
            .map(newGenreStrings::get)
            .toList());

        log.info("{{}}", genreSelection.getTargetItems());
        log.info("{{}}", currentGenres);

        Window window = genreSelection.getScene().getWindow();

        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }


}
