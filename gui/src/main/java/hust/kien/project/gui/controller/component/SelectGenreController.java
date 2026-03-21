package hust.kien.project.gui.controller.component;

import hust.kien.project.core.book.BookService;
import hust.kien.project.core.book.BookService.BookWithGenres;
import hust.kien.project.core.book.ReadonlyBook;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.controlsfx.control.ListSelectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class SelectGenreController {
    
    private static final Logger log = LoggerFactory.getLogger(SelectGenreController.class);
    
    @FXML
    private ListSelectionView<String> genreSelection;
    
    @FXML
    private TextField filterTextField;
    
    private final Set<String> currentGenres;
    
    public SelectGenreController(List<String> genres) {
        this.currentGenres = new HashSet<>(genres);
    }
    
    public void initialize() {

    }
    
    @FXML
    private void handleFilterByName() {
        log.error("NOT IMPLEMENTED");
//        genreSelection
//                .getSourceItems()
//                .setAll(newGenreStrings
//                        .keySet()
//                        .stream()
//                        .filter(name -> name.contains(filterTextField.getText()))
//                        .toList());
    }
    
    @FXML
    private void handleCompleted() {
        
        currentGenres.addAll(genreSelection.getTargetItems());
        
        log.debug("Select genre - after selection, genre Set: {{}}", currentGenres);
        
        Window window = genreSelection.getScene().getWindow();
        
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
    
}
