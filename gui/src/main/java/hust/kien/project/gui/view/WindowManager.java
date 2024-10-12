package hust.kien.project.gui.view;

import javafx.scene.Scene;
import javafx.stage.Stage;

/**
 * Expose a limited capability of {@link Stage}.
 */
public class WindowManager {
    private final Stage mainStage;
    
    public WindowManager(Stage mainStage) {
        this.mainStage = mainStage;
    }
    
    public Stage getStage() {
        return mainStage;
    }
    
    public void show() {
        mainStage.show();
    }
    
    public void setSize(
        double prefHeight,
        double prefWidth,
        double minHeight,
        double minWidth) {
        
        mainStage.setMinHeight(minHeight);
        mainStage.setMinWidth(minWidth);
        setSize(prefHeight, prefWidth);
    }
    
    public void setSize(double prefHeight, double prefWidth) {
        mainStage.setHeight(prefHeight);
        mainStage.setWidth(prefWidth);
        mainStage.centerOnScreen();
    }
    
    public void setScene(
        Scene scene,
        String title,
        double prefHeight,
        double prefWidth,
        double minHeight,
        double minWidth) {
        
        setSize(prefHeight, prefWidth, minHeight, minWidth);
        setScene(scene, title);
    }
    
    public void setScene(Scene scene, String title, double prefHeight, double prefWidth) {
        setSize(prefHeight, prefWidth);
        setScene(scene, title);
    }
    
    public void setScene(Scene scene, String title) {
        mainStage.setTitle(title);
        setScene(scene);
    }
    
    public void setScene(Scene scene) {
        scene.getStylesheets().clear();
        scene.getStylesheets().add("/css/style.css");
        mainStage.setScene(scene);
    }
}
