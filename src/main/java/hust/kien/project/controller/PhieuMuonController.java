package hust.kien.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.controlsfx.control.textfield.TextFields;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ComboBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class PhieuMuonController implements Initializable{

	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_search;

	@FXML
	private TableView<?> table;

	@FXML
	private TableColumn<?, ?> col_id;

	@FXML
	private TableColumn<?, ?> col_ngaybatdau;

	@FXML
	private TableColumn<?, ?> col_ngayketthuc;

	@FXML
	private TableColumn<?, ?> col_sach;

	@FXML
	private TableColumn<?, ?> col_nguoidung;

	@FXML
	private TextField txt_id;

	@FXML
	private DatePicker date_ngaybatdau;

	@FXML
	private DatePicker date_ngayketthuc;

	@FXML
	private ComboBox<?> choice_sach;

	@FXML
	private ComboBox<?> choice_nguoidung;

	@FXML
	void suaKhuyenMai(ActionEvent event) {

	}

	@FXML
	void themKhuyenMai(ActionEvent event) {
		
	}

	@FXML
	void timKiemSach(KeyEvent event) {

	}

	@FXML
	void xoaKhuyenMai(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();
		searchChoiceBox();
	}
	
	public void setWidthColumn() {
		col_id.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		col_ngaybatdau.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_ngayketthuc.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_nguoidung.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
		col_sach.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
	}
	
	public void searchChoiceBox() {
		choice_sach.setEditable(true);
		TextFields.bindAutoCompletion(choice_sach.getEditor(), choice_sach.getItems());
		choice_nguoidung.setEditable(true);
		TextFields.bindAutoCompletion(choice_nguoidung.getEditor(), choice_nguoidung.getItems());
	}

}
