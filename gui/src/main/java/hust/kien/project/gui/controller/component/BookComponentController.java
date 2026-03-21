package hust.kien.project.gui.controller.component;

import hust.kien.project.core.author.AuthorService;
import hust.kien.project.core.author.ReadonlyAuthor;
import hust.kien.project.core.book.BookService;
import hust.kien.project.core.book.ReadonlyBook;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.controller.utils.LeftAnchorPaneProperty;
import hust.kien.project.gui.controller.utils.RightAnchorPaneProperty;
import hust.kien.project.gui.view.event.BookDeletedEvent;
import javafx.animation.*;
import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;

import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

import static hust.kien.project.gui.controller.component.BookComponentUtils.*;

public class BookComponentController {

    private static final Logger log = LoggerFactory.getLogger(BookComponentController.class);

    private static final Duration fadeDuration = Duration.millis(600);

    private static final Duration animationDuration = Duration.millis(400);

    @FXML
    private Text stock;

    @FXML
    private TextField authorName;

    @FXML
    private Button modifyAuthor;

    @FXML
    private TextArea bookName;

    @FXML
    private ImageView bookImage;

    @FXML
    private TextField money;

    @FXML
    private Button viewMoreButton;

    @FXML
    private AnchorPane extraContainer;

    @FXML
    private ToggleButton modifyBookButton;

    @FXML
    private FlowPane genreContainer;

    @FXML
    private TextField releasedYear;

    @FXML
    private Button addGenreButton;

    @FXML
    private Button borrowBookButton;

    @FXML
    private Button returnBookButton;

    private ReadonlyBook book;

    private @Nullable List<String> genres;

    @Autowired
    private BookService bookService;

    private final Image image;

    private ParallelTransition openTabAnimation;

    private ParallelTransition closeTabAnimation;

    private boolean tabEverOpened = false;

    private Map<ToggleButton, String> genresOnUi;

    @Autowired
    @Qualifier("selectGenreStage")
    private ObjectProvider<Stage> selectGenreProvider;

    @Autowired
    @Qualifier("selectAuthorStage")
    private ObjectProvider<Stage> selectAuthorProvider;

    @Autowired
    @Qualifier("selectClientStage")
    private ObjectProvider<Stage> selectClientProvider;

    @Autowired
    private AuthorService authorService;

    @Autowired
    private NumberFormat formatter;

    private static final String STOCK_STRING = "Ton kho: ";

    public BookComponentController(ReadonlyBook book, Image image) {
//        this.defensiveCopy = getDefensiveCopy(book.getBookInfo(), book.getBookStock());
        this.book = book;
        this.image = image;
    }

    public void initialize() {
        renderBasicInformation();

        bookImage.setImage(image);
        bookImage.setFitHeight(137);

        modifyAuthor.setVisible(false);
        extraContainer.setVisible(false);
        toggleElementOpacity(addGenreButton, 0.4);
    }

    private void renderBasicInformation() {
        String bookString = book.name();

        List<ReadonlyAuthor> authors = authorService.authorsOf(book.id());

        String authorString = authors
                .stream()
                .map(ReadonlyAuthor::name)
                .collect(Collectors.joining(", "));

        renderBookAndAuthorNameWithMaxLength(bookString, authorString);

        String stockString = STOCK_STRING + 0;

        String moneyString = formatter.format(0);
        stock.setText(stockString);
        money.setText(moneyString);
    }

    private void renderBookAndAuthorNameWithMaxLength(String bookString, String authorString) {
        setTextElementWithMaxLength(bookName, bookString, 40);
        setTextElementWithMaxLength(authorName, authorString, 18);
    }

    private void renderExtraInformation() {
        LocalDate releasedDate = book.releasedDate();
        String yearString = releasedDate == null ? "N/A" : String.valueOf(releasedDate.getYear());

        genresOnUi = new HashMap<>();

        releasedYear.setText(yearString);
        if (this.genres == null) {
            this.genres = bookService.genresOf(book.id());
        }
        this.genres.forEach(this::createTBAndAddToUI);
    }

    @FXML
    private void handleClick() {
        if (!tabEverOpened) {
            openTabFirstTimeInitialization();
            tabEverOpened = true;
        }
        if (extraContainer.isVisible()) {
            getCloseTabAnimation().play();
        } else {
            extraContainer.setVisible(true);
            getOpenTabAnimation().play();
        }
    }

    @FXML
    private void handleDeleteBook() {
        Optional<ButtonType> response = AlertUtils.showAndWaitOkCancelAlert("Are you sure?");

        if (response.isEmpty() || response.get() == ButtonType.CANCEL) {
            // Do nothing
        } else if (response.get() == ButtonType.OK) {
            bookService.delete(book.id());
            extraContainer.fireEvent(new BookDeletedEvent(book));
            log.info("Delete book request: {{}}", book);
        } else {
            throw new IllegalStateException("can't be here, should be only OK Cancel and Close");
        }

    }

    @FXML
    private void handleBorrowBook(MouseEvent event) {
        log.info("Borrow book request: {{}}", book);
        Stage stage = selectClientProvider.getObject(book, true);
        stage.show();
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ev -> stock.setText(STOCK_STRING +
                (Integer.parseInt(stock.getText().substring(9)) - 1)));
    }

    @FXML
    private void handleReturnBook() {
        log.info("Return book request: {}", book);
        Stage stage = selectClientProvider.getObject(book, false);
        stage.show();
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ev -> stock.setText(STOCK_STRING +
                (Integer.parseInt(stock.getText().substring(9)) + 1)));
    }

    @FXML
    private void handleModifyBook(MouseEvent event) {
        if (modifyBookButton.isSelected()) {
            modifyBookButton.setText("Hoan tat");
        } else {
            modifyBookButton.setText("Sua sach");
            if (validateAndRevertFields()) {
                commitBook();
                renderBasicInformation();
            }
        }
        toggleEditable();
    }

    @FXML
    private void handleModifyAuthor(MouseEvent event) {
        if (modifyBookButton.isSelected()) {
            log.info("Modify author requests: {{}}", book);
            Stage stage = selectAuthorProvider.getObject(book);
            stage.show();
        }
    }

    @FXML
    private void handleAddGenre() {
        assert this.genres != null;

        if (modifyBookButton.isSelected()) {
            Stage stage = selectGenreProvider.getObject(genres);
            stage.show();
            stage.getScene().getWindow()
                    .addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ev -> renderNewBookGenres());
        }
    }

    private void renderNewBookGenres() {
        Collection<String> currentlyRenderedBookGenres = genresOnUi.values();

//        book.getBookInfo().bookGenres().stream()
//                .filter(bg -> !currentlyRenderedBookGenres.contains(bg))
//                .forEach(bg -> createTBAndAddToUI(bg).setDisable(false));
    }

    private void openTabFirstTimeInitialization() {
        getOpenTabAnimation();
        getCloseTabAnimation();
        renderExtraInformation();
    }

    /**
     * Create Book Genre Toggle Button and add it to UI
     */
    private ToggleButton createTBAndAddToUI(String genre) {
        ToggleButton toggleButton = new ToggleButton(genre);
        toggleButton.getStyleClass().addAll("alatsi", "borrow-book");
        toggleButton.setDisable(true);

        genresOnUi.put(toggleButton, genre);

        genreContainer.getChildren().add(toggleButton);

        return toggleButton;
    }

    private ParallelTransition getOpenTabAnimation() {
        if (tabEverOpened) {
            return openTabAnimation;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(animationDuration,
                        new KeyValue(new LeftAnchorPaneProperty(viewMoreButton), 630.0)),
                new KeyFrame(animationDuration,
                        new KeyValue(new LeftAnchorPaneProperty(extraContainer), 320.0)),
                new KeyFrame(animationDuration,
                        new KeyValue(new RightAnchorPaneProperty(extraContainer), 50.0)));
        timeline.setOnFinished(ev -> viewMoreButton.setText("<"));

        FadeTransition fadeIn = new FadeTransition(fadeDuration, extraContainer);
        fadeIn.setFromValue(0.0);
        fadeIn.setToValue(1.0);

        openTabAnimation = new ParallelTransition(timeline, fadeIn);

        return openTabAnimation;
    }

    private ParallelTransition getCloseTabAnimation() {
        if (tabEverOpened) {
            return closeTabAnimation;
        }

        Timeline timeline = new Timeline(
                new KeyFrame(animationDuration,
                        new KeyValue(new LeftAnchorPaneProperty(viewMoreButton), 320.0,
                                Interpolator.EASE_IN)),
                new KeyFrame(animationDuration,
                        new KeyValue(new LeftAnchorPaneProperty(extraContainer), 360.0)),
                new KeyFrame(animationDuration,
                        new KeyValue(new RightAnchorPaneProperty(extraContainer), -300.0)));
        timeline.setOnFinished(ev -> viewMoreButton.setText(">"));

        FadeTransition fadeOut = new FadeTransition(fadeDuration, extraContainer);
        fadeOut.setFromValue(1.0);
        fadeOut.setToValue(0.0);
        fadeOut.setOnFinished(ev -> extraContainer.setVisible(false));

        closeTabAnimation = new ParallelTransition(timeline, fadeOut);
        return closeTabAnimation;
    }

    private void toggleEditable() {
        boolean editable = modifyBookButton.isSelected();

        toggleTextFieldsEditable(editable);
        toggleElementOpacity(addGenreButton, editable ? 1.0 : 0.4);
        modifyAuthor.setVisible(editable);
        authorName.setVisible(!editable);

        if (editable) {
            bookName.setText(book.name());
        } else {
            setTextElementWithMaxLength(bookName, book.name(), 40);
        }

        for (ToggleButton genreToggleButton : genresOnUi.keySet()) {
            genreToggleButton.setDisable(!editable);
        }

        borrowBookButton.setDisable(editable);
        toggleElementOpacity(borrowBookButton, !editable ? 1.0 : 0.4);
        returnBookButton.setDisable(editable);
        toggleElementOpacity(returnBookButton, !editable ? 1.0 : 0.4);
    }

    private void toggleTextFieldsEditable(boolean editable) {
        bookName.setEditable(editable);
        bookName.setFocusTraversable(editable);
        releasedYear.setEditable(editable);
        releasedYear.setFocusTraversable(editable);
        money.setEditable(editable);
        money.setFocusTraversable(editable);
    }

    private boolean validateAndRevertFields() {
        if (!isIntegerValid(releasedYear.getText()) || !isDoubleValid(money.getText())) {
            revertEveryThing();
            return false;
        }

//        book.getBookInfo().setName(bookName.getText());
//        book.getBookInfo().setReleasedYear(Integer.parseInt(releasedYear.getText()));
//        book.getBookStock()
//                .setReimburseCost(Double.parseDouble(money.getText().replaceAll("[' ,]", "")));
//
//        genresOnUi.keySet().removeIf(ToggleButton::isSelected);
//
//        log.debug("Book genre set before deleting unselected genres: {{}}",
//                book.getBookInfo().bookGenres());
//
//        // delete selected BookGenres
//        book.getBookInfo()
//                .bookGenres()
//                .retainAll(genresOnUi.values());
//
//        rerenderBookGenres(book.getBookInfo().bookGenres());
        return true;
    }

    private void rerenderBookGenres(Iterable<String> genres) {
        genresOnUi.clear();
        genreContainer.getChildren().removeIf(ToggleButton.class::isInstance);
        log.error("NOT IMPLEMENTED");
//        for (BookGenre bookGenre : genres) {
//            createTBAndAddToUI(bookGenre);
//        }
    }

    private void revertEveryThing() {
        log.error("NOT IMPLEMENTED");
//        renderBookAndAuthorNameWithMaxLength(
//                defensiveCopy.getBookInfo().name(),
//                defensiveCopy.getBookInfo().authors()
//                        .stream()
//                        .map(a -> a.name())
//                        .collect(Collectors.joining(", ")));
//        releasedYear.setText(String.valueOf(defensiveCopy.getBookInfo().releasedYear()));
//        money.setText(String.valueOf(defensiveCopy.getBookStock().reimburseCost()));
//
//        List<BookGenre> oldBookGenres = defensiveCopy.getBookInfo().bookGenres();
//
//        List<BookGenre> bookGenres = book.getBookInfo().bookGenres();
//        bookGenres.retainAll(oldBookGenres);
//        rerenderBookGenres(bookGenres);
//
//        validateYearTextField(null);
//        validateMoneyTextField(null);
    }

    private void commitBook() {
        log.error("NOT IMPLEMENTED");
//        log.debug("Book Genres set before commit: {{}}", book.getBookInfo().bookGenres());
//        defensiveCopy = getDefensiveCopy(book.getBookInfo(), book.getBookStock());
//        book = librarianService.saveOrUpdate(book);
    }

    @FXML
    private void validateYearTextField(KeyEvent event) {
        log.debug("Validating year: {} ", isIntegerValid(releasedYear.getText()));
        setElementBorderFromValidationResult(releasedYear, isIntegerValid(releasedYear.getText()));
    }

    @FXML
    private void validateMoneyTextField(KeyEvent event) {
        log.debug("Validating money: {}", isDoubleValid(money.getText()));
        setElementBorderFromValidationResult(money, isDoubleValid(money.getText()));
    }

}
