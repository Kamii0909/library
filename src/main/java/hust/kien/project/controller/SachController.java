package hust.kien.project.controller;

import java.net.URL;
import java.util.ResourceBundle;

import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

public class SachController implements Initializable{

	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_search;

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

	@FXML
	void timKiemSach(KeyEvent event) {
		
	}
	
	public void setWidthColumn() {
		col_tensach.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
		col_namxuatban.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		col_tacgia.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_theloai.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_masach.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();
	}
}
