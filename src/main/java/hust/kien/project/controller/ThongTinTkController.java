package hust.kien.project.controller;

import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;

@Component
@SuppressWarnings("all")
public class ThongTinTkController {

	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_tendangnhap;

	@FXML
	private PasswordField txt_passcu;

	@FXML
	private PasswordField txt_passmoi;

	@FXML
	private PasswordField txt_repass;

	@FXML
	private TextField txt_hoten;

	@FXML
	private ChoiceBox<?> choice_quyen;

	@FXML
	private Button btn_xoaTK;

	@FXML
	void capNhatTaiKhoan(ActionEvent event) {
		// TODO? 
	}

	@FXML
	void xoaTaiKhoan(ActionEvent event) {
		// TODO?
	}
}
