package hust.kien.project.gui.controller.client;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.PasswordField;
import javafx.scene.control.RadioButton;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.ToggleGroup;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;

@Component
public class ThongTinTKClientController implements Initializable {

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
	private RadioButton sachDaMuon;

	@FXML
	private RadioButton sachQuaHan;

	@FXML
	private TableView<?> table;

	@FXML
	private TableColumn<?, ?> col_tacgia;

	@FXML
	private TableColumn<?, ?> col_masach;

	@FXML
	private TableColumn<?, ?> col_tensach;

	@FXML
	private TableColumn<?, ?> col_namxuatban;

	@FXML
	private TableColumn<?, ?> col_theloai;

	ToggleGroup toggleGroup = new ToggleGroup();

	@FXML
	void capNhatTaiKhoan(ActionEvent event) {

	}

	@FXML
	void sachDaMuon(MouseEvent event) {

	}

	@FXML
	void sachQuaHan(MouseEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();
		setGroupRadio();
	}

	public void setGroupRadio() {
		sachDaMuon.setToggleGroup(toggleGroup);
		sachQuaHan.setToggleGroup(toggleGroup);
		// listen to changes in selected toggle
		/*
		 * toggleGroup.selectedToggleProperty().addListener( (observable, oldVal,
		 * newVal) -> // xu ly System.out.println(newVal + " was selected" ));
		 */
	}

	public void setWidthColumn() {
		col_tensach.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
		col_namxuatban.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		col_tacgia.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_theloai.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_masach.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
	}

}
