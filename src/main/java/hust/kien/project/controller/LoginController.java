package hust.kien.project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.context.ApplicationEventPublisherAware;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;
import hust.kien.project.controller.utils.AlertUtils;
import hust.kien.project.service.auth.AuthService;
import hust.kien.project.service.auth.AuthorizedContextHolder;
import hust.kien.project.service.auth.BadCredentialException;
import hust.kien.project.service.auth.NoUserFoundException;
import hust.kien.project.view.event.LoginSuccessEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

@Component
@Lazy
public final class LoginController implements Initializable, ApplicationEventPublisherAware {
	private final AuthService authService;

	private ApplicationEventPublisher eventPublisher;

	private final Resource bannerImage;

	// Java Fx Fields
	@FXML
	private ImageView banner;

	@FXML
	private HBox hboxBanner;

	@FXML
	private TextField usernameInputField;

	@FXML
	private PasswordField passwordInputField;

	@FXML
	private CheckBox rememberMeCheckBox;

	public LoginController(AuthService authService,
		@Value("classpath:/image/login_banner.jpg") Resource bannerImage) {
		this.authService = authService;
		this.bannerImage = bannerImage;
	}

	@FXML
	void login(ActionEvent event) {
		String usernameInput = usernameInputField.getText();
		String passwordInput = passwordInputField.getText();

		AuthorizedContextHolder auth;
		try {
			auth = authService.auth(usernameInput, passwordInput);
		} catch (NoUserFoundException | BadCredentialException e) {
			AlertUtils.showAlert(e.getMessage(), AlertType.ERROR);
			return;
		} catch (Exception e) {
			throw new IllegalStateException("Uncaught ???", e);
		}
		eventPublisher.publishEvent(new LoginSuccessEvent(auth));

		if (rememberMeCheckBox.isSelected()) {
			// TODO: do something to remember user
		}
	}

	@FXML
	void quenMatKhau(MouseEvent event) {
		// TODO: do something to change password
		AlertUtils.showAlert("Hãy liên hệ với quản trị viên", AlertType.INFORMATION);
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		try {
			setBannerLogin();
		} catch (IOException e) {
			throw new IllegalStateException("Error reading log in banner image", e);
		}
		readFile();
	}

	public void writeFile(String content) throws FileNotFoundException {
		File myObj = new File("remember.txt");
		PrintWriter writer = new PrintWriter(myObj);
		writer.print(content);
		writer.close();
	}

	public void readFile() {
		String username = "";
		String password = "";
		try {
			File myObj = new File("remember.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				if (username.equals("")) {
					username = myReader.nextLine();
				}
				if (password.equals("")) {
					password = myReader.nextLine();
				}
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		usernameInputField.setText(username);
		passwordInputField.setText(password);
	}

	public void setBannerLogin() throws IOException {
		Image image = new Image(bannerImage.getInputStream());
		banner.setImage(image);
		banner.fitHeightProperty().bind(hboxBanner.heightProperty());
		banner.fitWidthProperty().bind(hboxBanner.widthProperty());
	}

	@Override
	public void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher) {
		this.eventPublisher = applicationEventPublisher;
	}



}
