package hust.kien.project.controller.utils;

import java.util.Optional;
import javafx.scene.control.Alert;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Alert.AlertType;

public class AlertUtils {

	private AlertUtils() {}

	public static void showAlert(String str, AlertType type) {
		Alert alert = new Alert(type, str, ButtonType.OK);
		alert.setHeaderText(null);
		alert.show();
	}

	public static Alert createAlert(String str, AlertType type) {
		Alert alert = new Alert(type, str, ButtonType.OK);
		alert.setHeaderText(null);
		return alert;
	}

	public static Optional<ButtonType> showAndWaitOkCancelAlert(String str) {
		Alert alert = new Alert(AlertType.CONFIRMATION, str, ButtonType.OK, ButtonType.CANCEL);

		alert.setHeaderText(null);
		return alert.showAndWait();
	}
}
