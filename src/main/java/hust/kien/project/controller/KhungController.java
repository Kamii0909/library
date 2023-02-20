package hust.kien.project.controller;

import java.io.IOException;
import java.net.URL;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.ResourceBundle;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import hust.kien.project.service.auth.AuthorizedContextHolder;
import hust.kien.project.view.WindowManager;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.stage.Window;

@Component
@Lazy
public class KhungController {

	private final Resource avatarImageResource;
	private final Resource logoImageResource;
	private final ObjectProvider<Region> manageBookRegionProvider;
	private final ObjectProvider<Region> manageClientRegionProvider;
	private final ObjectProvider<Region> manageAuthorRegionProvider;
	private final ObjectProvider<Region> manageGenreRegionProvider;
	private final ObjectProvider<Region> manageTicketRegionProvider;
	private final ObjectProvider<Region> userInformationRegionProvider;
	private final ObjectProvider<Region> statisticRegionProvider;
	private final ObjectProvider<Region> introductionPageRegionProvider;


	public KhungController(@Qualifier("avatarImage") Resource avatarImage,
		@Qualifier("logoImage") Resource logoImage,
		@Qualifier("manageBookRegion") ObjectProvider<Region> manageBookRegionProvider,
		@Qualifier("manageClientRegion") ObjectProvider<Region> manageClientRegionProvider,
		@Qualifier("manageAuthorRegion") ObjectProvider<Region> manageAuthorRegionProvider,
		@Qualifier("manageGenreRegion") ObjectProvider<Region> manageGenreRegionProvider,
		@Qualifier("manageTicketRegion") ObjectProvider<Region> manageTicketRegionProvider,
		@Qualifier("userInformationRegion") ObjectProvider<Region> userInformationRegionProvider,
		@Qualifier("statisticRegion") ObjectProvider<Region> statisticRegionProvider,
		@Qualifier("introductionPageRegion") ObjectProvider<Region> introductionPageRegionProvider) {
		this.avatarImageResource = avatarImage;
		this.logoImageResource = logoImage;
		this.introductionPageRegionProvider = introductionPageRegionProvider;
		this.manageBookRegionProvider = manageBookRegionProvider;
		this.manageClientRegionProvider = manageClientRegionProvider;
		this.manageAuthorRegionProvider = manageAuthorRegionProvider;
		this.manageGenreRegionProvider = manageGenreRegionProvider;
		this.userInformationRegionProvider = userInformationRegionProvider;
		this.statisticRegionProvider = statisticRegionProvider;
		this.manageTicketRegionProvider = manageTicketRegionProvider;
	}


	@FXML
	private AnchorPane mainContent;

	@FXML
	private VBox accountMenu;

	@FXML
	private GridPane navBar;

	@FXML
	private Button manageBookButton;

	@FXML
	private Button manageAuthorButton;

	@FXML
	private Button manageGenreButton;

	@FXML
	private Button manageClientButton;

	@FXML
	private Button userInformationButton;

	@FXML
	private Button manageTicketButton;

	@FXML
	private Button introductionPageButton;

	@FXML
	private Button statisticButton;

	@FXML
	private ImageView avatar;

	@FXML
	private ImageView logo;

	private Map<Button, ObjectProvider<Region>> buttonRegionMap = new LinkedHashMap<>(8);

	@FXML
	void toggleAccountMenu() {
		if (accountMenu.isVisible()) {
			accountMenu.setVisible(false);
			accountMenu.setPrefWidth(0);
			accountMenu.setMinWidth(0);
		} else {
			accountMenu.setMinWidth(200);
			accountMenu.setPrefWidth(200);
			accountMenu.setVisible(true);
		}
	}

	public void initialize() {
		toggleAccountMenu();

		setImage(avatar, avatarImageResource);
		setImage(logo, logoImageResource);

		changeRegionOnContentPane(introductionPageRegionProvider.getIfUnique());
	}

	public void initializeButtonEventListener(AuthorizedContextHolder authContext) {
		hideAllButtons();

		buttonRegionMap.put(introductionPageButton, introductionPageRegionProvider);

		if (authContext.getLibrarianService() != null) {
			buttonRegionMap.putAll(Map.of(
				manageBookButton, manageBookRegionProvider,
				manageAuthorButton, manageAuthorRegionProvider,
				manageGenreButton, manageGenreRegionProvider,
				manageClientButton, manageClientRegionProvider,
				manageTicketButton, manageTicketRegionProvider));
		}
		if (authContext.getManagerService() != null) {
			buttonRegionMap.putAll(Map.of(
				statisticButton, statisticRegionProvider,
				userInformationButton, userInformationRegionProvider));
		}
		if (authContext.getAuditService() != null) {
			// TODO
		}

		int i = 0;
		for (Entry<Button, ObjectProvider<Region>> entry : buttonRegionMap.entrySet()) {

			Button button = entry.getKey();
			Region content = entry.getValue().getIfUnique();
			
			navBar.add(button, 0, i++);

			RowConstraints rowConstraints = new RowConstraints();
			rowConstraints.setPercentHeight(100);
			navBar.getRowConstraints().add(rowConstraints);

			button.setOnMouseClicked(ev -> {
				clearAllButtonsStyle();

				button.setStyle("-fx-background-color: #d3e3fd");
				button.setTextFill(Color.BLACK);

				changeRegionOnContentPane(content);
			});
		}
	}

	private void hideAllButtons() {
		navBar.getRowConstraints().clear();
		navBar.getChildren().clear();
	}

	private void clearAllButtonsStyle() {
		for (Button button : buttonRegionMap.keySet()) {
			button.setStyle("-fx-background-color: #3f4a59");
			button.setTextFill(Color.WHITE);
		}
	}

	private void changeRegionOnContentPane(Region region) {
		assert region != null;

		mainContent.getChildren().clear();
		mainContent.getChildren().add(region);

		region.prefWidthProperty().bind(WindowManager.getStage().widthProperty().subtract(180));
		region.prefHeightProperty().bind(WindowManager.getStage().heightProperty().subtract(100));

		FxUtils.setAnchorPoint(0, 0, 0, 0, region);
	}

	private static void setImage(ImageView imageContainer, Resource image) {
		try {
			imageContainer.setImage(new Image(image.getInputStream()));
		} catch (IOException e) {
			throw new IllegalStateException("Can't load image " + image.getFilename()
				+ " on container " + imageContainer, e);
		}
	}
}
