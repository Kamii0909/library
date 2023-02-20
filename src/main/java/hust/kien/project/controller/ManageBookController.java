package hust.kien.project.controller;

import static hust.kien.project.controller.FxUtils.createAnchoredButton;
import static hust.kien.project.controller.FxUtils.createAnchoredText;
import static hust.kien.project.controller.FxUtils.createAnchoredTextArea;
import static hust.kien.project.controller.FxUtils.createAnchoredTextField;
import static hust.kien.project.controller.FxUtils.setAnchorPoint;
import static hust.kien.project.controller.FxUtils.setHeigth;
import static hust.kien.project.controller.FxUtils.setWidth;
import java.io.IOException;
import java.util.stream.Collectors;
import org.controlsfx.tools.Utils;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;
import hust.kien.project.view.WindowManager;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.fxml.FXML;
import javafx.geometry.Bounds;
import javafx.scene.control.Button;
import javafx.scene.control.TextArea;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManageBookController {

	private static final String FONT_ALATSI = "alatsi";

	// private static final String FONT_ALEGREYA = "alegreya-sans-light-italic";

	@FXML
	private FlowPane bookContainer;

	private final LibrarianService librarianService;

	private final Image defaultImage;

	private final ObjectProvider<Region> bookComponentRegionProvider;

	/**
	 * Hacky
	 * 
	 * @see #renderBookInformation(String, String, String)
	 */
	private double oldHeight = 0;


	public ManageBookController(LibrarianService librarianService,
		@Qualifier("defaultBookImage") Resource defaultBookImageResource,
		@Qualifier("bookComponentRegion") ObjectProvider<Region> bookComponentRegionProvider)
		throws IOException {
		this.librarianService = librarianService;
		this.defaultImage = new Image(defaultBookImageResource.getInputStream(), 0, 0,
			true, true);
		this.bookComponentRegionProvider = bookComponentRegionProvider;
	}


	public void initialize() {
		bookContainer.getChildren().clear();

		bookContainer.getChildren().addAll(librarianService
			.dynamicFind(new BookSpecificationBuilder().initCollection().authors().back())
			.stream()
			.limit(10)
			.map(this::renderOneBook)
			.collect(Collectors.toList()));

		log.info("{}", ((AnchorPane) bookContainer.getChildren().get(0)).getWidth());
	}

	private AnchorPane renderOneBook(Book book) {

		String formattedAuthorName = book.getBookInfo().getAuthors().stream()
			.map(Author::getAuthorInfo).map(AuthorInfo::getName).collect(Collectors.joining(", "));

		if (formattedAuthorName.length() > 18) {
			formattedAuthorName = formattedAuthorName.substring(0, 17) + "...";
		}

		String formattedBookName = book.getBookInfo().getName();

		if (formattedBookName.length() > 60) {
			formattedBookName = formattedBookName.substring(0, 59) + "...";
		}

		String formattedMoney = Double.toString(book.getBookStock().getReimburseCost());

		AnchorPane anchorPane =
			new AnchorPane(renderStockAnchorPane(book.getBookStock().getStock()),
				renderImage(defaultImage),
				renderBookInformation(formattedAuthorName, formattedBookName, formattedMoney),
				renderOpenButton());

		anchorPane.getStyleClass().add("product");

		setWidth(200, null, 800, anchorPane);
		setHeigth(100, null, 400, anchorPane);

		return anchorPane;
	}



	private AnchorPane renderStockAnchorPane(int stock) {

		Text stockText = new Text("Tồn kho: " + stock);
		stockText.getStyleClass().add(FONT_ALATSI);

		setAnchorPoint(0, null, 7, 7, stockText);

		AnchorPane stockAnchorPane = new AnchorPane(stockText);
		stockAnchorPane.getStyleClass().add("product-number");

		setAnchorPoint(0, null, 20, null, stockAnchorPane);
		setHeigth(null, 22, null, stockAnchorPane);

		return stockAnchorPane;
	}

	private Pane renderImage(Image image) {

		double width = image.getWidth();
		double height = image.getHeight();

		ImageView imageView = new ImageView();
		imageView.setPreserveRatio(true);

		int newHeigth = (int) height;
		int newWidth = (int) width;
		int x = 0;
		int y = 0;

		if (height / width > 1.4) {
			newHeigth = (int) (width * 1.4);
			y = ((int) (newHeigth - height) / 2);
		} else {
			newWidth = (int) (height / 1.4);
			x = ((int) (width - newWidth) / 2);
		}
		imageView.setImage(new WritableImage(image.getPixelReader(), x, y, newWidth, newHeigth));
		imageView.setFitWidth(100);
		imageView.setFitHeight(140);

		FxUtils.clipChildren(imageView, 10.0, 100.0, 140.0);

		Pane pane = new StackPane(imageView);
		pane.getStyleClass().add("image-container");

		pane.setPrefSize(100, 140);
		setAnchorPoint(28, 15, 15, null, pane);

		return pane;
	}

	private AnchorPane renderBookInformation(String authorFormatted, String bookFormatted,
		String moneyFormatted) {

		TextArea bookText = createAnchoredTextArea(
			bookFormatted, false, false, true,
			20, 30, 0, 0,
			"editable-text", FONT_ALATSI, "book-name");

		bookText.setPrefRowCount(1);

		AnchorPane pane = new AnchorPane(
			createAnchoredTextField(authorFormatted, false, false,
				0, null, 0, 0,
				"editable-text", FONT_ALATSI, "author"),
			bookText,
			createAnchoredText("VNĐ",
				null, 0, 0, null,
				FONT_ALATSI, "money"),
			createAnchoredTextField(moneyFormatted, false, false,
				null, 0, 50, 0,
				FONT_ALATSI, "money"));

		setAnchorPoint(40, 25, 140, null, pane);
		setWidth(null, 165, null, pane);

		return pane;
	}

	private Button renderOpenButton() {
		Button button = createAnchoredButton(">", 10, 10, 320, 10, "view-more", FONT_ALATSI);

		button.setOnMouseClicked(ev -> log.info("Cliked"));

		button.setPrefWidth(30);

		return button;
	}

}
