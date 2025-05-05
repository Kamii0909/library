package hust.kien.project.gui.controller.frame;

import static de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon.FILE_ARCHIVE_ALT;

import java.util.LinkedHashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import de.jensd.fx.glyphs.fontawesome.FontAwesomeIcon;
import de.jensd.fx.glyphs.fontawesome.FontAwesomeIconView;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.controller.utils.FxUtils;
import hust.kien.project.gui.pages.introduction.Introduction;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.Button;
import javafx.scene.control.ContentDisplay;
import javafx.scene.control.Tooltip;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;

@Lazy
public class MainFrameController {
	
	private static final Logger log = LoggerFactory.getLogger(MainFrameController.class);
	
	private final ObjectProvider<Region> manageBookRegionProvider;
	
	private final ObjectProvider<Region> manageClientRegionProvider;
	
	private final ObjectProvider<Region> manageGenreAuthorRegionProvider;
	
	private final ObjectProvider<Region> manageTicketRegionProvider;
	
	private final ObjectProvider<Region> userInformationRegionProvider;
	
	private final ObjectProvider<Region> managerFrameRegionProvider;
	
	private final Introduction introductionTab;
	
	public MainFrameController(
		@Qualifier("manageBookRegion") ObjectProvider<Region> manageBookRegionProvider,
		@Qualifier("manageClientRegion") ObjectProvider<Region> manageClientRegionProvider,
		@Qualifier("manageGenreAuthorRegion") ObjectProvider<Region> manageGenreAuthorRegionProvider,
		@Qualifier("manageTicketRegion") ObjectProvider<Region> manageTicketRegionProvider,
		@Qualifier("userInformationRegion") ObjectProvider<Region> userInformationRegionProvider,
		@Qualifier("managerFrameRegion") ObjectProvider<Region> managerFrameRegionProvider,
		Introduction introductionTab) {
		this.introductionTab = introductionTab;
		this.manageBookRegionProvider = manageBookRegionProvider;
		this.manageClientRegionProvider = manageClientRegionProvider;
		this.manageGenreAuthorRegionProvider = manageGenreAuthorRegionProvider;
		this.userInformationRegionProvider = userInformationRegionProvider;
		this.managerFrameRegionProvider = managerFrameRegionProvider;
		this.manageTicketRegionProvider = manageTicketRegionProvider;
	}
	
	@FXML
	private AnchorPane mainContent;
	
	@FXML
	private Rectangle navigator;
	
	@FXML
	private VBox navBar;
	
	private Map<StackPane, Region> navBarButtons = new LinkedHashMap<>();
	
	public void initialize() {
		Region introduction = introductionTab.element();
		
		initButtonAndAddToMap(FILE_ARCHIVE_ALT, "Gioi thieu", introduction, 10);
		
		changeMainContent(introduction, 10);
	}
	
	public void initializeButtonEventListener(AuthorizedContextHolder authenticationPrincipal) {
		int i = 1;
		
		if (authenticationPrincipal.getLibrarianService() != null) {
			initButtonAndAddToMap(FontAwesomeIcon.BOOK, "Quan ly sach",
					manageBookRegionProvider.getObject(), 10 + (i++) * 51);
			initButtonAndAddToMap(FontAwesomeIcon.CUBES, "Quan ly the loai",
					manageGenreAuthorRegionProvider.getObject(), 10 + (i++) * 51);
			initButtonAndAddToMap(FontAwesomeIcon.VCARD_ALT, "Quan ly khach hang",
					manageClientRegionProvider.getObject(), 10 + (i++) * 51);
			
		}
		if (authenticationPrincipal.getManagerService() != null) {
			initButtonAndAddToMap(FontAwesomeIcon.AREA_CHART, "Thong ke",
					managerFrameRegionProvider.getObject(), 10 + (i++) * 51);
		}
		if (authenticationPrincipal.getAuditService() != null) {
			initButtonAndAddToMap(FontAwesomeIcon.EDIT, "Quan ly phieu muon",
					manageTicketRegionProvider.getObject(), 10 + (i++) * 51);
		}
		
	}
	
	private void initButtonAndAddToMap(FontAwesomeIcon icon,
		String tooltip,
		Region region,
		int navPosition) {
		StackPane stackPane = createStackPane(icon, tooltip, region, navPosition);
		navPosition += 50;
		
		navBar.getChildren().add(stackPane);
		navBarButtons.put(stackPane, region);
	}
	
	private void changeMainContent(Region region, int top) {
		mainContent.getChildren().setAll(region);
		FxUtils.setAnchorPoint(top, null, null, null, navigator);
		FxUtils.setAnchorPoint(0, 0, 0, 0, region);
		
	}
	
	private StackPane createStackPane(FontAwesomeIcon icon,
		String tooltip,
		Region region,
		int navPosition) {
		
		FontAwesomeIconView fontAwesomeIconView = new FontAwesomeIconView(icon);
		fontAwesomeIconView.setSize("22");
		
		Button button = new Button();
		button.setGraphic(fontAwesomeIconView);
		button.setContentDisplay(ContentDisplay.GRAPHIC_ONLY);
		button.setOnMouseClicked(ev -> changeMainContent(region, navPosition));
		
		Tooltip buttonTooltip = new Tooltip(tooltip);
		buttonTooltip.setShowDelay(Duration.millis(100));
		Tooltip.install(button, buttonTooltip);
		
		StackPane stackPane = new StackPane(button);
		stackPane.getStyleClass().setAll("navButton");
		FxUtils.setHeigth(40, 40, 40, stackPane);
		
		return stackPane;
	}
	
	@FXML
	private void handleLogout() {
		log.info("Log out request");
		AlertUtils.showAlert("Tinh nang tam thoi chua duoc trien khai", AlertType.INFORMATION);
	}
	
	@FXML
	private void handleModifyUser() {
		log.info("Modify user request");
		Region region = userInformationRegionProvider.getObject();
		mainContent.getChildren().setAll(region);
		FxUtils.setAnchorPoint(0, 0, 0, 0, region);
	}
	
}
