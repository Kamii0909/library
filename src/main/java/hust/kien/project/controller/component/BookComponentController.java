package hust.kien.project.controller.component;

import static hust.kien.project.controller.component.BookComponentUtils.isMoneyValid;
import static hust.kien.project.controller.component.BookComponentUtils.isYearValid;
import static hust.kien.project.controller.component.BookComponentUtils.setElementBorderFromValidationResult;
import static hust.kien.project.controller.component.BookComponentUtils.setTextElementWithMaxLength;
import static hust.kien.project.controller.component.BookComponentUtils.toggleElementOpacity;
import java.text.NumberFormat;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import hust.kien.project.controller.AlertUtils;
import hust.kien.project.controller.utils.LeftAnchorPaneProperty;
import hust.kien.project.controller.utils.RightAnchorPaneProperty;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.service.authorized.LibrarianService;
import javafx.animation.FadeTransition;
import javafx.animation.Interpolator;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.ParallelTransition;
import javafx.animation.Timeline;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ButtonType;
import javafx.scene.control.TextArea;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleButton;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import javafx.util.Duration;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class BookComponentController {

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

    private final Book book;

    private final Image image;

    private static final Duration animationDuration = Duration.millis(400);

    private static final Duration fadeDuration = Duration.millis(600);

    private ParallelTransition openTabAnimation;

    private ParallelTransition closeTabAnimation;

    private boolean tabEverOpened = false;

    private Map<ToggleButton, BookGenre> uiToModelMap;

    private Set<BookGenre> oldBookGenres;

    @Autowired
    @Qualifier("selectGenreStage")
    private ObjectProvider<Stage> selectGenreProvider;

    @Autowired
    private LibrarianService librarianService;

    @Autowired
    private NumberFormat formatter;

    public BookComponentController(Book book, Image image) {
        this.book = book;
        this.image = image;
        this.oldBookGenres = book.getBookInfo()
            .getBookGenres()
            .stream()
            .collect(Collectors.toSet());
    }

    public void initialize() {
        renderBasicInformation();

        bookImage.setImage(image);

        modifyAuthor.setVisible(false);
        extraContainer.setVisible(false);
        toggleElementOpacity(addGenreButton, 0.4);
    }

    private void renderBasicInformation() {
        String bookString = book.getBookInfo().getName();

        String authorString = book.getBookInfo()
            .getAuthors()
            .stream()
            .map(Author::getAuthorInfo)
            .map(AuthorInfo::getName)
            .collect(Collectors.joining(", "));

        renderBookAndAuthorNameWithMaxLength(bookString, authorString);

        String stockString = "Ton kho: " + book.getBookStock().getStock();

        String moneyString = formatter.format(book.getBookStock().getReimburseCost());
        stock.setText(stockString);
        money.setText(moneyString);
    }

    private void renderBookAndAuthorNameWithMaxLength(String bookString, String authorString) {
        setTextElementWithMaxLength(bookName, bookString, 40);
        setTextElementWithMaxLength(authorName, authorString, 18);
    }

    private void renderExtraInformation() {
        String yearString = Integer.toString(book.getBookInfo().getReleasedYear());

        uiToModelMap = new HashMap<>();

        releasedYear.setText(yearString);
        genreContainer.getChildren().addAll(book.getBookInfo()
            .getBookGenres()
            .stream()
            .map(this::createToggleButtonForEachGenreAndAddToMap)
            .toList());
    }

    @FXML
    private void handleClick(ActionEvent event) {
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
    private void handleDeleteBook(MouseEvent event) {
        Optional<ButtonType> response = AlertUtils.showAndWaitOkCancelAlert("Are you sure?");

        if (response.isEmpty() || response.get() == ButtonType.CANCEL) {
            // Do nothing
        } else if (response.get() == ButtonType.OK) {
            // Hacky stuff
            ((Pane) extraContainer.getParent().getParent())
                .getChildren()
                .remove(extraContainer.getParent());
            librarianService.delete(book);
            log.info("Delete book request: {{}}", book);
        } else {
            throw new IllegalStateException("can't be here, should be only OK Cancel and Close");
        }

    }

    @FXML
    private void handleBorrowBook(MouseEvent event) {
        log.info("Borrow book request: {{}}", book);

    }

    @FXML
    private void handleModifyBook(MouseEvent event) {
        if (modifyBookButton.isSelected()) {
            modifyBookButton.setText("Hoan tat");
        } else {
            modifyBookButton.setText("Sua sach");
            if (validateAndRevertFields()) {
                commitBook();
            }
        }
        toggleEditable();
    }

    @FXML
    private void handleModifyAuthor(MouseEvent event) {
        log.info("Modify author requests: {{}}", book);
    }


    @FXML
    private void handleAddGenre(MouseEvent event) {
        if (modifyBookButton.isSelected()) {
            log.info("Add genre request: {}", librarianService);
            Stage stage = selectGenreProvider.getObject(book);
            stage.show();
            stage.getScene().getWindow()
                .addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ev -> renderNewBookGenres());
        }
    }

    private void renderNewBookGenres() {

        Collection<BookGenre> bookGenresCurrentlyRendered = uiToModelMap.values();

        genreContainer.getChildren().addAll(book.getBookInfo()
            .getBookGenres()
            .stream()
            .filter(bg -> !bookGenresCurrentlyRendered.contains(bg))
            .map(bg -> {
                ToggleButton tb = createToggleButtonForEachGenreAndAddToMap(bg);
                tb.setDisable(false);
                return tb;
            })
            .toList());
    }



    private void openTabFirstTimeInitialization() {
        getOpenTabAnimation();
        getCloseTabAnimation();
        renderExtraInformation();
    }

    private ToggleButton createToggleButtonForEachGenreAndAddToMap(BookGenre genre) {
        ToggleButton toggleButton = new ToggleButton(genre.getName());
        toggleButton.getStyleClass().addAll("alatsi", "borrow-book");
        toggleButton.setDisable(true);

        uiToModelMap.put(toggleButton, genre);

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
            bookName.setText(book.getBookInfo().getName());
        } else {
            setTextElementWithMaxLength(bookName, book.getBookInfo().getName(), 40);
        }

        for (ToggleButton genreToggleButton : uiToModelMap.keySet()) {
            genreToggleButton.setDisable(!editable);
        }
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
        if (!isYearValid(releasedYear.getText()) || !isMoneyValid(money.getText())) {
            revertEveryThing();
            return false;
        }

        book.getBookInfo().setName(bookName.getText());
        book.getBookInfo().setReleasedYear(Integer.parseInt(releasedYear.getText()));
        book.getBookStock()
            .setReimburseCost(Double.parseDouble(money.getText().replaceAll("[' ,]", "")));

        book.getBookInfo()
            .getBookGenres()
            .retainAll(uiToModelMap.entrySet()
                .stream()
                .filter(entry -> {
                    boolean toBeRemoved = entry.getKey().isSelected();
                    if (toBeRemoved) {
                        genreContainer.getChildren().remove(entry.getKey());
                    }
                    return !toBeRemoved;
                })
                .map(Entry::getValue)
                .toList());
        return true;
    }

    private void revertEveryThing() {
        bookName.setText(book.getBookInfo().getName());
        releasedYear.setText(String.valueOf(book.getBookInfo().getReleasedYear()));
        money.setText(String.valueOf(book.getBookStock().getReimburseCost()));

        book.getBookInfo().getBookGenres().retainAll(oldBookGenres);

        log.info("Reverting: Old book genres {{}}", oldBookGenres);
        uiToModelMap.values().retainAll(oldBookGenres);

        genreContainer.getChildren()
            .removeIf(tg -> !uiToModelMap.containsKey(tg) && tg instanceof ToggleButton);

        uiToModelMap.keySet().forEach(tb -> tb.setSelected(false));

        validateYearTextField(null);
        validateMoneyTextField(null);
    }

    private void commitBook() {
        oldBookGenres = book.getBookInfo().getBookGenres().stream().collect(Collectors.toSet());
        librarianService.saveOrUpdate(book);
    }

    @FXML
    private void validateYearTextField(KeyEvent event) {
        log.debug("Validating year: {} ", isYearValid(releasedYear.getText()));
        setElementBorderFromValidationResult(releasedYear, isYearValid(releasedYear.getText()));
    }

    @FXML
    private void validateMoneyTextField(KeyEvent event) {
        log.debug("Validating money: {}", isMoneyValid(money.getText()));
        setElementBorderFromValidationResult(money, isMoneyValid(money.getText()));
    }
}
