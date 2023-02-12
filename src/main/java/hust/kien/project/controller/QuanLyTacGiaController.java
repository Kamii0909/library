package hust.kien.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class QuanLyTacGiaController implements Initializable{

	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_search;

	@FXML
	private TableView<?> table;

	@FXML
	private TableColumn<?, ?> col_id;

	@FXML
	private TableColumn<?, ?> col_tentg;

	@FXML
	private TableColumn<?, ?> col_tuoi;

	@FXML
	private TextField txt_id_tacgia;

	@FXML
	private TextField txt_tentacgia;

	@FXML
	private TextField txt_tuoi;

	@FXML
	void suaTacGia(ActionEvent event) {

	}

	@FXML
	void themTacGia(ActionEvent event) {

	}

	@FXML
	void timKiemSach(KeyEvent event) {

	}

	@FXML
	void xemDanhSachSach(ActionEvent event) {

	}

	@FXML
	void xoaTacGia(ActionEvent event) {

	}

	@FXML
	void searchTacGia(KeyEvent event) {
		System.out.println(txt_search.getText());
	}
	
	public void setWidthColumn() {
		col_id.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_tentg.prefWidthProperty().bind(table.widthProperty().multiply(0.5));
		col_tuoi.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();
	}

}
