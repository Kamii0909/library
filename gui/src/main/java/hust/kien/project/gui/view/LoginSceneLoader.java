package hust.kien.project.gui.view;

import org.springframework.beans.factory.annotation.Value;

import hust.kien.project.gui.pages.Page;
import hust.kien.project.gui.pages.login.LoginPage;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class LoginSceneLoader {
    
    private final String applicationTitle;
    
    private final WindowManager windowManager;
    
    private final Page page;
    
    LoginSceneLoader(
        @Value("${spring.application.ui.login.title}") String applicationTitle,
        LoginPage page,
        WindowManager windowManager) {
        
        this.applicationTitle = applicationTitle;
        this.windowManager = windowManager;
        this.page = page;
    }
    
    public void showLoginScene() {
        Stage stage = windowManager.getStage();
        stage.setTitle(applicationTitle);
        Scene scene = stage.getScene();
        if (scene == null)
            stage.setScene(new Scene(page.show()));
        else
            scene.setRoot(page.show());
        windowManager.show();
    }
    
}
