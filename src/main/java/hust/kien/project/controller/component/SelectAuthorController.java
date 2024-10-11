package hust.kien.project.controller.component;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.controlsfx.control.ListSelectionView;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.AuthorSpecificationBuilder;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class SelectAuthorController {
    
    private static final Logger log = LoggerFactory.getLogger(SelectAuthorController.class);
    
    @FXML
    private ListSelectionView<String> authorSelection;
    
    @FXML
    private TextField nameFilter;
    
    @FXML
    private TextField ageFilterFrom;
    
    @FXML
    private TextField ageFilterTo;
    
    private Map<Long, Author> allAuthors;
    
    private Set<Author> currentAuthors;
    
    private Set<Author> newAuthors;
    
    public SelectAuthorController(Book book, LibrarianService librarianService) {
        this.currentAuthors = book.getBookInfo().getAuthors();
        
        this.allAuthors = librarianService.dynamicFind(new AuthorSpecificationBuilder())
                .stream()
                .collect(Collectors.toMap(Author::getId, Function.identity()));
        this.newAuthors = allAuthors.values()
                .stream()
                .filter(a -> !currentAuthors.contains(a))
                .collect(Collectors.toSet());
    }
    
    public void initialize() {
        authorSelection.getSourceItems().setAll(newAuthors.stream()
                .map(SelectAuthorController::getDisplayString)
                .toList());
        
        authorSelection.getTargetItems().setAll(currentAuthors.stream()
                .map(SelectAuthorController::getDisplayString)
                .toList());
    }
    
    private static String getDisplayString(Author author) {
        return String.format("%s(%d tuoi) // %d",
                author.getAuthorInfo().getName(),
                author.getAuthorInfo().getAge(),
                author.getId());
    }
    
    private Author parseDisplayStringToAuthor(String string) {
        return allAuthors.get(Long.parseLong(
                string,
                string.lastIndexOf('/') + 2,
                string.length(),
                10));
    }
    
    @FXML
    private void handleFilter() {
        log.debug("Filter request name: {} - age from: {} - age to: {} ",
                nameFilter.getText(),
                ageFilterFrom.getText(),
                ageFilterTo.getText());
        
        Set<Author> authorsToBeDisplayed = new HashSet<>(allAuthors.values());
        authorsToBeDisplayed.removeAll(authorSelection.getTargetItems()
                .stream()
                .map(this::parseDisplayStringToAuthor)
                .toList());
        if (BookComponentUtils.isIntegerValid(ageFilterFrom.getText())) {
            authorsToBeDisplayed.removeIf(
                    a -> a.getAuthorInfo().getAge() < Integer.parseInt(ageFilterFrom.getText()));
        }
        if (BookComponentUtils.isIntegerValid(ageFilterTo.getText())) {
            authorsToBeDisplayed.removeIf(
                    a -> a.getAuthorInfo().getAge() > Integer.parseInt(ageFilterTo.getText()));
        }
        if (!nameFilter.getText().isBlank()) {
            authorsToBeDisplayed
                    .removeIf(a -> !a.getAuthorInfo().getName().contains(nameFilter.getText()));
        }
        authorSelection.getSourceItems().setAll(authorsToBeDisplayed
                .stream()
                .map(SelectAuthorController::getDisplayString)
                .toList());
    }
    
    @FXML
    private void handleCompleted() {
        
        Set<Author> chosenAuthors = authorSelection.getTargetItems()
                .stream()
                .map(this::parseDisplayStringToAuthor)
                .collect(Collectors.toSet());
        
        currentAuthors.addAll(chosenAuthors);
        currentAuthors.retainAll(chosenAuthors);
        
        Window window = authorSelection.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
