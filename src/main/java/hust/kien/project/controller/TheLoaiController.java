package hust.kien.project.controller;

import java.net.URL;
import java.util.ResourceBundle;
import org.springframework.stereotype.Component;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

@Component
public class TheLoaiController implements Initializable{

	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_search;

	@FXML
	private TableView<?> table;

	@FXML
	private TableColumn<?, ?> col_id;

	@FXML
	private TableColumn<?, ?> col_tentheloai;

	@FXML
	private TextField txt_id_theloai;

	@FXML
	private TextField txt_tentheloai;

	@FXML
	void suaTheLoai(ActionEvent event) {

	}

	@FXML
	void themTheLoai(ActionEvent event) {

	}

	@FXML
	void timKiemSach(KeyEvent event) {

	}

	@FXML
	void xemDanhSachSach(ActionEvent event) {

	}

	@FXML
	void xoaTheLoai(ActionEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();
		
	}
	
	public void setWidthColumn() {
		col_id.prefWidthProperty().bind(table.widthProperty().multiply(0.4));
		col_tentheloai.prefWidthProperty().bind(table.widthProperty().multiply(0.6));
	}
}
