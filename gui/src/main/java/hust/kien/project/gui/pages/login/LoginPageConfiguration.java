package hust.kien.project.gui.pages.login;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

@Configuration
@Import({ LoginController.class, LoginPage.class })
public class LoginPageConfiguration {
    
}
