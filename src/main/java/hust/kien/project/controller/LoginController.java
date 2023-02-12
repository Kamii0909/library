package hust.kien.project.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

public final class LoginController implements Initializable {

	@FXML
	private ImageView banner;

	@FXML
	private HBox hboxBanner;

	@FXML
	private TextField txt_username;

	@FXML
	private PasswordField txt_password;

	@FXML
	private CheckBox ghiNho;

	@FXML
	void login(ActionEvent event) throws FileNotFoundException {
		if (!txt_username.getText().isBlank() && !txt_password.getText().isBlank()) {
			if (ghiNho.isSelected()) {
				writeFile(txt_username.getText() + "\n"+txt_password.getText());
			}
			else {
				writeFile("");
			}
		}
	}

	@FXML
	void quenMatKhau(MouseEvent event) {
		Message.getMess("Hãy liên hệ với quản trị viên", 1);
	}

	@FXML
	void remember(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setBannerLogin();
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
		txt_username.setText(username);
		txt_password.setText(password);
	}

	public void setBannerLogin() {
		File file = new File("image/bannerLogin.jpg");
		String path = null;
		try {
			path = file.toURI().toURL().toString();
		} catch (MalformedURLException e) {
			e.printStackTrace();
		}
		Image image = new Image(path);
		banner.setImage(image);
		banner.fitHeightProperty().bind(hboxBanner.heightProperty());
	}

}
