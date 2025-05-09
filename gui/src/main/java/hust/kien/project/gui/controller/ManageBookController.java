package hust.kien.project.gui.controller;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;

import hust.kien.project.core.book.BookSchema;
import hust.kien.project.core.book.BookService;
import hust.kien.project.core.book.internal.BookFilter;
import hust.kien.project.core.model.book.Book;
import hust.kien.project.gui.controller.component.BookComponentUtils;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.controller.utils.FxUtils;
import hust.kien.project.gui.view.event.BookDeletedEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Text;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;

@Lazy
public class ManageBookController {
	
	private static final Logger log = LoggerFactory.getLogger(ManageBookController.class);
	
	@FXML
	private TextField stockField;
	
	@FXML
	private TextField bookNameField;
	
	@FXML
	private TextField yearStartField;
	
	@FXML
	private TextField yearEndField;
	
	@FXML
	private HBox genreFilterBar;
	
	@FXML
	private HBox authorFilterBar;
	
	@FXML
	private TextField genreFilterText;
	
	@FXML
	private TextField authorFilterText;
	
	@FXML
	private FlowPane bookContainer;
	
	@FXML
	private Text currentPageText;
	
	private final BookService bookService;
	
	private final Image defaultImage;
	
	private int currentPage = 1;
	
	private final ObjectProvider<AnchorPane> bookComponentRegionProvider;
	
	private final ObjectProvider<Stage> newBookRegionProvider;
	
	private List<Book> filteredBooks;
	
	public ManageBookController(BookService bookService,
		@Qualifier("defaultBookImage") Resource defaultBookImageResource,
		@Qualifier("bookComponentRegion") ObjectProvider<AnchorPane> bookComponentRegionProvider,
		@Qualifier("newBookStage") ObjectProvider<Stage> newBookRegionProvider)
		throws IOException {
		this.bookService = bookService;
		this.defaultImage =
				FxUtils.renderImage(new Image(defaultBookImageResource.getInputStream(), 0, 0,
						true, true), 1.4);
		this.bookComponentRegionProvider = bookComponentRegionProvider;
		this.newBookRegionProvider = newBookRegionProvider;
	}
	
	public void initialize() {
		filteredBooks = bookService.find(new BookFilter(), new BookSchema().authors().genres());
		bookContainer.getChildren().setAll(filteredBooks
				.stream()
				.limit(10)
				.map(t -> bookComponentRegionProvider.getObject(t, defaultImage))
				.collect(Collectors.toList()));
		
		bookContainer.addEventFilter(BookDeletedEvent.BOOK_DELETED_EVENT, ev -> {
			BookDeletedEvent event = ((BookDeletedEvent) ev);
			log.info("Deleted book: {}", event.getDeletedBook());
			currentPage = 1;
			updateCurrentPage();
		});
	}
	
	@FXML
	private void handleAddGenreFilter() {
		Button button = new Button(genreFilterText.getText());
		button.setOnMouseClicked(_ -> {
			genreFilterBar.getChildren().remove(button);
			handleFilter();
		});
		button.getStyleClass().addAll("alatsi", "borrow-book");
		
		genreFilterText.clear();
		genreFilterBar.getChildren().add(button);
		
		handleFilter();
	}
	
	@FXML
	private void handleAddAuthorFilter() {
		Button button = new Button(authorFilterText.getText());
		button.setOnMouseClicked(ev -> {
			authorFilterBar.getChildren().remove(button);
			handleFilter();
		});
		button.getStyleClass().addAll("alatsi", "borrow-book");
		
		authorFilterText.clear();
		authorFilterBar.getChildren().add(button);
		
		handleFilter();
	}
	
	@FXML
	private void handleNextPage() {
		if (currentPage != filteredBooks.size() / 10 + (filteredBooks.size() % 10 == 0 ? 0 : 1)) {
			currentPage++;
			updateCurrentPage();
		} else {
			AlertUtils.showAlert("Da o trang cuoi cung", AlertType.ERROR);
		}
		log.info("Next page request {}", currentPage);
	}
	
	@FXML
	private void handleLastPage() {
		if (currentPage != 1) {
			currentPage--;
			updateCurrentPage();
			
		} else {
			AlertUtils.showAlert("Da o trang dau tien", AlertType.ERROR);
		}
		log.info("Handle last page request {}", currentPage);
	}
	
	private void updateCurrentPage() {
		currentPageText.setText("Trang: " + currentPage);
		refreshElements(filteredBooks);
	}
	
	@FXML
	private void handleFilter() {
		currentPage = 1;
		currentPageText.setText("Trang: " + currentPage);
		refreshElements(doFilter());
	}
	
	@FXML
	private void handleAddBook() {
		log.info("Adding book request");
		Stage stage = newBookRegionProvider.getObject();
		stage.show();
		stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ev -> {
			currentPage = 1;
			filteredBooks = doFilter();
			updateCurrentPage();
		});
	}
	
	/**
	 * This will refresh the UI without changing current page
	 * 
	 * @param books
	 */
	private void refreshElements(List<Book> books) {
		filteredBooks = books;
		
		bookContainer.getChildren().setAll(books
				.subList(currentPage * 10 - 10, Math.min(filteredBooks.size(), currentPage * 10))
				.stream()
				.map(b -> bookComponentRegionProvider.getObject(b, defaultImage))
				.toList());
	}
	
	private List<Book> doFilter() {
		// TODO: debounce
		log.info("Filtering");
		
		BookFilter filter = new BookFilter();
		BookSchema schema = new BookSchema().authors().genres();
		
		if (BookComponentUtils.isIntegerValid(stockField.getText())) {
			int stockMin = Integer.parseInt(stockField.getText());
			filter.stockBetween(stockMin, 10000);
		}
		if (!bookNameField.getText().isBlank()) {
			filter.nameContains(bookNameField.getText());
		}
		int yearStart = 0;
		int yearEnd = 10000;
		if (BookComponentUtils.isIntegerValid(yearStartField.getText())) {
			yearStart = Integer.parseInt(yearStartField.getText());
		}
		if (BookComponentUtils.isIntegerValid(yearEndField.getText())) {
			yearEnd = Integer.parseInt(yearEndField.getText());
		}
		filter.releasedBetween(yearStart, yearEnd);
		
		if (!genreFilterBar.getChildren().isEmpty()) {
			filter.withEachGenreLikeAtLeastOne(genreFilterBar.getChildren().stream()
					.map(Button.class::cast).map(Button::getText).toList());
		}
		
		if (!authorFilterBar.getChildren().isEmpty()) {
			filter.fromAllAuthorsWithNameLike(authorFilterBar.getChildren().stream()
					.map(Button.class::cast).map(Button::getText).toList());
		}
		
		return bookService.find(filter, schema);
	}
}
