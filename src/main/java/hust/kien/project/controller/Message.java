package hust.kien.project.controller;

import javafx.scene.control.Alert;

public class Message {

	public static void getMess(String str, int type) {
		Alert alert = new Alert(Alert.AlertType.INFORMATION);
		if(type == 2) {
			alert.alertTypeProperty().set(Alert.AlertType.ERROR);
		}
		alert.setContentText(str);
		alert.show();
	}
}
