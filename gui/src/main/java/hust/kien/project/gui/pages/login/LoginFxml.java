package hust.kien.project.gui.pages.login;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationContext;

import hust.kien.project.core.service.auth.AuthService;
import hust.kien.project.gui.pages.FxmlBinding;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

class LoginFxml extends FxmlBinding<@NonNull State, @NonNull Actions> {
    
    private final ApplicationContext context;
    
    @SuppressWarnings("null")
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
        handler().login(event);
    }
    
    @FXML
    private void quenMatKhau(MouseEvent event) {
        handler().quenMatKhau(event);
    }
    
    @NonNull
    @Override
    public LoginController createController() {
        return new LoginController(
                context.getBean(AuthService.class),
                context,
                bind());
    }
    
    @Override
    public State bind() {
        State state = initializeState();
        Credentials credentials = readFromFile();
        if (credentials != null) {
            state.username().setValue(credentials.username);
            state.password().setValue(credentials.password);
        }
        
        return state;
    }
    
    private State initializeState() {
        return new State(
                usernameInputField.textProperty(),
                passwordInputField.textProperty(),
                rememberMeCheckBox.selectedProperty());
    }
    
    private record Credentials(String username, String password) {
    }
    
    private static @Nullable Credentials readFromFile() {
        try {
            var lines = Files.lines(new File("remember.txt").toPath()).toList();
            if (lines.size() != 2)
                return null;
            return new Credentials(lines.get(0), lines.get(1));
        } catch (IOException e) {
            return null;
        }
    }
}
