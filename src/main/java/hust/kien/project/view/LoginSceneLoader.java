package hust.kien.project.view;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;

import javafx.scene.Scene;
import javafx.scene.layout.Region;

public class LoginSceneLoader {
    
    private final String applicationTitle;
    
    private final Scene loginScene;
    
    private final WindowManagerBean windowManager;
    
    LoginSceneLoader(
        @Value("${spring.application.ui.login.title}") String applicationTitle,
        @Qualifier("loginRegion") Region loginRegion,
        WindowManagerBean windowManager) {
        
        this.applicationTitle = applicationTitle;
        this.loginScene = new Scene(loginRegion);
        this.windowManager = windowManager;
    }
    
    public void showLoginScene() {
        windowManager.setScene(loginScene, applicationTitle);
        windowManager.show();
    }
    
}
