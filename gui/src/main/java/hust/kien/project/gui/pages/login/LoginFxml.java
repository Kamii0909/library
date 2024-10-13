package hust.kien.project.gui.pages.login;

import org.springframework.context.ApplicationContext;

import hust.kien.project.core.service.auth.AuthService;
import hust.kien.project.gui.pages.Fxml;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

class LoginFxml extends Fxml<State, LoginController> {
    
    private final ApplicationContext context;
    
    public LoginFxml(LoginPage page, ApplicationContext context) {
        super(page);
        this.context = context;
    }
    
    @FXML
    private TextField usernameInputField;
    
    @FXML
    private PasswordField passwordInputField;
    
    @FXML
    private CheckBox rememberMeCheckBox;
    
    @FXML
    private void login(ActionEvent event) {
        controller().login(event);
    }
    
    @FXML
    private void quenMatKhau(MouseEvent event) {
        controller().quenMatKhau(event);
    }
    
    @Override
    public LoginController createController() {
        return new LoginController(
                context.getBean(AuthService.class),
                context,
                initializeState());
    }
    
    private State initializeState() {
        return new State(
                usernameInputField.textProperty(),
                passwordInputField.textProperty(),
                rememberMeCheckBox.selectedProperty());
    }
}
