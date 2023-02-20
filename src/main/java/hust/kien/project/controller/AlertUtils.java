package hust.kien.project.controller;

import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

	private AlertUtils() {}

	public static void showAlert(String str, AlertType type) {
		Alert alert = new Alert(type, str, ButtonType.OK);
		alert.show();
	}
}
