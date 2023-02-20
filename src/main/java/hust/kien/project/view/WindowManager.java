package hust.kien.project.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

public class WindowManager {

    private static Stage mainStage;

    private WindowManager() {}

    public static Stage getStage() {
        return mainStage;
    }

    public static void show() {
        mainStage.show();
    }

    public static void setSize(
        double prefHeight, double prefWidth,
        double minHeight, double minWidth) {
        mainStage.setMinHeight(minHeight);
        mainStage.setMinWidth(minWidth);
        setSize(prefHeight, prefWidth);
    }

    public static void setSize(double prefHeight, double prefWidth) {
        mainStage.setHeight(prefHeight);
        mainStage.setWidth(prefWidth);
        mainStage.centerOnScreen();
    }

    public static void setScene(
        Scene scene, String title,
        double prefHeight, double prefWidth,
        double minHeight, double minWidth) {
        setSize(prefHeight, prefWidth, minHeight, minWidth);
        setScene(scene, title);
    }

    public static void setScene(Scene scene, String title, double prefHeight, double prefWidth) {
        setSize(prefHeight, prefWidth);
        setScene(scene, title);
    }

    public static void setScene(Scene scene, String title) {
        mainStage.setTitle(title);
        setScene(scene);
    }

    public static void setScene(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("/css/style.css");
        mainStage.setScene(scene);
    }

    public static void setStage(Stage stage) {
        mainStage = stage;
    }
}
