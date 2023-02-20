package hust.kien.project.controller.client;

import java.net.URL;
import java.util.ResourceBundle;

import org.controlsfx.control.textfield.TextFields;
import org.springframework.stereotype.Component;
import javafx.fxml.Initializable;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.ComboBox;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.HBox;

@Component
public class SachClientController implements Initializable {
	
	@FXML
	private HBox hBoxContent;

	@FXML
	private TextField txt_search;

	@FXML
	private ComboBox<?> choiceTacgia;

	@FXML
	private ComboBox<?> choiceTenSach;

	@FXML
	private ComboBox<?> choiceNamXuatBan;

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
	void dangKyMuon(ActionEvent event) {

	}

	@FXML
	void locSach(ActionEvent event) {

	}

	@FXML
	void timKiemSach(KeyEvent event) {

	}

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		setWidthColumn();
		searchChoiceBox();
	}
	
	public void setWidthColumn() {
		col_tensach.prefWidthProperty().bind(table.widthProperty().multiply(0.3));
		col_namxuatban.prefWidthProperty().bind(table.widthProperty().multiply(0.1));
		col_tacgia.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_theloai.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
		col_masach.prefWidthProperty().bind(table.widthProperty().multiply(0.2));
	}
	
	public void searchChoiceBox() {
		choiceNamXuatBan.setEditable(true);
		TextFields.bindAutoCompletion(choiceNamXuatBan.getEditor(), choiceNamXuatBan.getItems());
		choiceTacgia.setEditable(true);
		TextFields.bindAutoCompletion(choiceTacgia.getEditor(), choiceTacgia.getItems());
		choiceTenSach.setEditable(true);
		TextFields.bindAutoCompletion(choiceTenSach.getEditor(), choiceTenSach.getItems());
	}

}
