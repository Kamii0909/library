package hust.kien.project.gui.controller;

import java.io.File;
import java.io.FileNotFoundException;
import java.net.URL;
import java.util.ResourceBundle;
import java.util.Scanner;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

@Component
public class GioiThieuController implements Initializable {

	@FXML
	private VBox vBox_Content;

	@Override
	public void initialize(URL location, ResourceBundle resources) {
		final WebView browser = new WebView();
		final WebEngine webEngine = browser.getEngine();

		ScrollPane scrollPane = new ScrollPane();
		scrollPane.setContent(browser);

		webEngine.loadContent(readFile());

		vBox_Content.getChildren().add(scrollPane);
	}

	public String readFile() {
		String str = "";
		try {
			File myObj = new File("gioithieu.txt");
			Scanner myReader = new Scanner(myObj);
			while (myReader.hasNextLine()) {
				String data = myReader.nextLine();
				str += data;
			}
			myReader.close();
		} catch (FileNotFoundException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return str;
	}

}
