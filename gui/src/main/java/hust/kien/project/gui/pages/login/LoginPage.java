package hust.kien.project.gui.pages.login;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;

import hust.kien.project.core.service.auth.AuthService;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;
import hust.kien.project.core.service.auth.BadCredentialException;
import hust.kien.project.core.service.auth.NoUserFoundException;
import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.pages.FxmlComponent;
import hust.kien.project.gui.pages.Page;
import hust.kien.project.gui.view.event.LoginSuccessEvent;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;

public class LoginPage extends FxmlComponent<@NonNull LoginPage> implements Page {

    private @FXML TextField usernameInputField;
    private @FXML PasswordField passwordInputField;
    private @FXML CheckBox rememberMeCheckBox;

    private State state;

    private final AuthService authService;
    private final ApplicationEventPublisher eventPublisher;

    @SuppressWarnings("null")
    public LoginPage(
            @Value("classpath:gui/login.fxml") Resource fxml,
            ResourceHelper helper,
            AuthService authService,
            ApplicationEventPublisher eventPublisher) {
        super(fxml, helper);
        this.authService = authService;
        this.eventPublisher = eventPublisher;
    }

    @FXML
    public void initialize() {
        State state = initializeState();
        Credentials credentials = readFromFile();
        if (credentials != null) {
            state.username().setValue(credentials.username);
            state.password().setValue(credentials.password);
        }
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

    @FXML
    private void login(ActionEvent event) {
        String usernameInput = state.username().get();
        String passwordInput = state.password().get();

        AuthorizedContextHolder auth;
        try {
            auth = authService.auth(usernameInput, passwordInput);
        } catch (NoUserFoundException | BadCredentialException e) {
            AlertUtils.showAlert(e.getMessage(), AlertType.ERROR);
            return;
        } catch (Exception e) {
            throw new IllegalStateException("Uncaught ???", e);
        }
        eventPublisher.publishEvent(new LoginSuccessEvent(auth));

        if (state.rememberMe().get()) {
            // TODO: do something to remember user
        }
    }

    @FXML
    private void quenMatKhau(MouseEvent event) {
        // TODO: do something to change password
        AlertUtils.showAlert("Hãy liên hệ với quản trị viên", AlertType.INFORMATION);
    }

    @Override
    public LoginPage component() {
        return this;
    }
}
