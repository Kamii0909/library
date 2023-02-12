package hust.kien.project.controller;


import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;

public class KhungClientController implements Initializable{

	private int checkSubMenu = 1;
	
	private List<Button> listButton = new ArrayList<Button>();
	
	@FXML
	private Button btnMuonSach;

	@FXML
	private Button btnTaiKhoan;

	@FXML
	private Button btnGioiThieu;

	@FXML
	private ImageView logo;

	@FXML
	private ImageView avatar;

	@FXML
	private VBox sub_menu;

	@FXML
	private HBox hBoxContent;

	@FXML
	void capNhatThongTin(ActionEvent event) {

	}

	@FXML
	void logOut(ActionEvent event) {

	}

	@FXML
	void openSubMenu(MouseEvent event) {
		if(checkSubMenu %2 != 0) {
			open_Sub_Menu();
		}
		else {
			close_Sub_Menu();
		}
		++checkSubMenu;
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		close_Sub_Menu();
		setAvatar();
		setLogo();
		clickButtonMenu();
		try {
			Region n = (Region)FXMLLoader.load(getClass().getResource("../gui/sachClient.fxml"));
			hBoxContent.getChildren().add(n);
			n.prefWidthProperty().bind(hBoxContent.widthProperty());
			n.prefHeightProperty().bind(hBoxContent.heightProperty());
		} catch (IOException e) {e.printStackTrace();}
	}
	
	public void clickButtonMenu() {
		listButton.add(btnMuonSach);
		listButton.add(btnTaiKhoan);
		listButton.add(btnGioiThieu);
		listButton.add(btnGioiThieu);
		for (Button b : listButton) {
			b.addEventHandler(MouseEvent.MOUSE_CLICKED, ev -> {
				for(Button p : listButton) {
					p.setStyle("-fx-background-color: #ff7c00");
					p.setTextFill(Color.WHITE);
				}
				b.setStyle("-fx-background-color: #d3e3fd");
				b.setTextFill(Color.BLACK);

				// set noi dung
				hBoxContent.getChildren().clear();
				if(b.equals(listButton.get(0))) {
					try {
						Region n = (Region)FXMLLoader.load(getClass().getResource("../gui/sachClient.fxml"));
						hBoxContent.getChildren().add(n);
						n.prefWidthProperty().bind(hBoxContent.widthProperty());
						n.prefHeightProperty().bind(hBoxContent.heightProperty());
					} catch (IOException e) {e.printStackTrace();}
				}
				else if(b.equals(listButton.get(1))) {
					try {
						Region n = (Region)FXMLLoader.load(getClass().getResource("../gui/taiKhoanGui.fxml"));
						hBoxContent.getChildren().add(n);
						n.prefWidthProperty().bind(hBoxContent.widthProperty());
						n.prefHeightProperty().bind(hBoxContent.heightProperty());
					} catch (IOException e) {e.printStackTrace();}
				}
				else if(b.equals(listButton.get(2))) {
					try {
						Region n = (Region)FXMLLoader.load(getClass().getResource("../gui/gioithieu.fxml"));
						hBoxContent.getChildren().add(n);
						n.prefWidthProperty().bind(hBoxContent.widthProperty());
						n.prefHeightProperty().bind(hBoxContent.heightProperty());
					} catch (IOException e) {e.printStackTrace();}
				}
			});
		}
	}
	
	public void open_Sub_Menu() {
		sub_menu.setVisible(true);
		sub_menu.setPrefWidth(200);
		sub_menu.setMinWidth(200);
		sub_menu.setMaxWidth(200);
	}

	public void close_Sub_Menu() {
		sub_menu.setVisible(false);
		sub_menu.setPrefWidth(0);
		sub_menu.setMinWidth(0);
		sub_menu.setMaxWidth(0);
	}
	
	public void setAvatar() {
		File is = new File("image/avatar.jpg");
		InputStream ip = null;
		try {
			ip = new FileInputStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image image = new Image(ip);
		avatar.setImage(image);
	}

	public void setLogo() {
		File is = new File("image/LOGO1.png");
		InputStream ip = null;
		try {
			ip = new FileInputStream(is);
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		}
		Image image = new Image(ip);
		logo.setImage(image);
	}

}
