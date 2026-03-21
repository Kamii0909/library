package hust.kien.project.gui.pages;

import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

import java.util.Map;

public final class Namespace {
    private Namespace() {
    }

    @SuppressWarnings("unchecked")
    public static <T> T get(Map<String, Object> namespace, String key) {
        return (T) namespace.get(key);
    }

    public static <T> T get(Map<String, Object> namespace, String key, Class<T> clazz) {
        return clazz.cast(get(namespace, key));
    }

    public static TextField textField(Map<String, Object> namespace, String fxId) {
        return (TextField) namespace.get(fxId);
    }

    public static PasswordField passwordField(Map<String, Object> namespace, String fxId) {
        return (PasswordField) namespace.get(fxId);
    }

    public static CheckBox checkBox(Map<String, Object> namespace, String fxId) {
        return (CheckBox) namespace.get(fxId);
    }

    public static WebView webView(Map<String, Object> namespace, String fxId) {
        return (WebView) namespace.get(fxId);
    }

    public static VBox vBox(Map<String, Object> namespace, String fxId) {
        return (VBox) namespace.get(fxId);
    }
}
