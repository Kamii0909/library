package hust.kien.project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class QuanLyNguoiDungController implements Initializable {

	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_search;

	@FXML
	private TableView<?> table;

	@FXML
	private TableColumn<?, ?> col_id;

	@FXML
	private TableColumn<?, ?> col_tennguoidung;

	@FXML
	private TableColumn<?, ?> col_diachi;

	@FXML
	private TableColumn<?, ?> col_ngaydangky;

	@FXML
	private TableColumn<?, ?> col_ngayketthuc;

	@FXML
	private TableColumn<?, ?> col_solanmuontoida;

	@FXML
	private TextField txt_id;

	@FXML
	private TextField txt_tennguoidung;

	@FXML
	private TextField txt_diachi;

	@FXML
	private DatePicker date_ngayketthuc;

	@FXML
	private TextField txt_solanmuontoida;

	@FXML
	void suaNguoiDung(ActionEvent event) {

	}

	@FXML
	void themNguoiDung(ActionEvent event) {

	}

	@FXML
	void timKiemSach(KeyEvent event) {

	}

	@FXML
	void xoaNguoiDung(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();

	}

	public void setWidthColumn() {
		col_diachi.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_id.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		col_ngaydangky.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_ngayketthuc.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_solanmuontoida.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		col_tennguoidung.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
	}
}
