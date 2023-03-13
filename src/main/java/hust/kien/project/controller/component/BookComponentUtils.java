package hust.kien.project.controller.component;

import javafx.scene.Node;
import javafx.scene.control.TextInputControl;

public class BookComponentUtils {

    private BookComponentUtils() {}

    public static boolean setElementBorderFromValidationResult(Node node, boolean isValid) {
        if (isValid) {
            node.setStyle("-fx-boder-color: none");
        } else {
            node.setStyle("-fx-border-color: red; -fx-border-radius: 6; -fx-border-width: 1.5");
        }

        return isValid;
    }

    public static boolean isIntegerValid(String year) {
        try {
            Integer.parseInt(year);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean isDoubleValid(String db) {
        try {
            db = db.replaceAll("[,' ]", "");
            Double.parseDouble(db);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static void toggleElementOpacity(Node node, double opacity) {
        node.setStyle("-fx-opacity:" + opacity);
    }

    public static void setTextElementWithMaxLength(TextInputControl node, String text,
        int maxLength) {
        node.setText(text.length() > maxLength ? text.substring(0, maxLength) + "..." : text);
    }

}
