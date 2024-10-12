package hust.kien.project.gui.controller.component;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.controlsfx.control.ListSelectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.book.BookGenre;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.core.service.dynamic.BookGenreSpecificationBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class SelectGenreController {
    
    private static final Logger log = LoggerFactory.getLogger(SelectGenreController.class);
    
    @FXML
    private ListSelectionView<String> genreSelection;
    
    @FXML
    private TextField filterTextField;
    
    private final Map<String, BookGenre> newGenreStrings;
    
    private final Set<BookGenre> currentGenres;
    
    public SelectGenreController(Book book, LibrarianService librarianService) {
        this.currentGenres = book.getBookInfo().getBookGenres();
        
        this.newGenreStrings = librarianService
                .dynamicFind(new BookGenreSpecificationBuilder())
                .stream()
                .filter(bg -> !currentGenres.contains(bg))
                .collect(Collectors.toMap(BookGenre::getName, Function.identity()));
    }
    
    public void initialize() {
        genreSelection.getSourceItems().addAll(newGenreStrings.keySet());
    }
    
    @FXML
    private void handleFilterByName() {
        genreSelection
                .getSourceItems()
                .setAll(newGenreStrings
                        .keySet()
                        .stream()
                        .filter(name -> name.contains(filterTextField.getText()))
                        .toList());
    }
    
    @FXML
    private void handleCompleted() {
        
        currentGenres.addAll(genreSelection.getTargetItems()
                .stream()
                .map(newGenreStrings::get)
                .toList());
        
        log.debug("Select genre - after selection, genre Set: {{}}", currentGenres);
        
        Window window = genreSelection.getScene().getWindow();
        
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}
