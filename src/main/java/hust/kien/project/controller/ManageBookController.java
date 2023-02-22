package hust.kien.project.controller;

import java.io.IOException;
import java.util.stream.Collectors;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;
import javafx.fxml.FXML;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.WritableImage;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.FlowPane;
import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class ManageBookController {


	// private static final String FONT_ALEGREYA = "alegreya-sans-light-italic";

	@FXML
	private FlowPane bookContainer;

	private final LibrarianService librarianService;

	private final Image defaultImage;

	private final ObjectProvider<AnchorPane> bookComponentRegionProvider;

	public ManageBookController(LibrarianService librarianService,
		@Qualifier("defaultBookImage") Resource defaultBookImageResource,
		@Qualifier("bookComponentRegion") ObjectProvider<AnchorPane> bookComponentRegionProvider)
		throws IOException {
		this.librarianService = librarianService;
		this.defaultImage = renderImage(new Image(defaultBookImageResource.getInputStream(), 0, 0,
			true, true));
		this.bookComponentRegionProvider = bookComponentRegionProvider;
	}


	public void initialize() {
		bookContainer.getChildren().setAll(librarianService
			.dynamicFind(new BookSpecificationBuilder().initCollection().authors().genres().back())
			.stream()
			.limit(10)
			.map(t -> bookComponentRegionProvider.getObject(t, defaultImage))
			.collect(Collectors.toList()));
	}

	private Image renderImage(Image image) {
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

		return new WritableImage(image.getPixelReader(), x, y, newWidth, newHeigth);
	}
}
