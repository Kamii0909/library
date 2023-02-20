package hust.kien.project.view;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

@Component
public class LoginSceneLoader {

    private final String applicationTitle;
    private final Scene loginScene;

    LoginSceneLoader(
        @Value("${spring.application.ui.login.title}") String applicationTitle,
        @Qualifier("loginRegion") Region loginRegion) {

        this.applicationTitle = applicationTitle;
        this.loginScene = new Scene(loginRegion);
    }

    public void showLoginScene() {
        WindowManager.setScene(loginScene, applicationTitle);
        WindowManager.show();
    }

}
