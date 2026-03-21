package hust.kien.project.gui.pages.login;

import hust.kien.project.core.service.auth.AuthService;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;
import hust.kien.project.core.service.auth.BadCredentialException;
import hust.kien.project.core.service.auth.NoUserFoundException;
import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.pages.Namespace;
import hust.kien.project.gui.pages.Page;
import hust.kien.project.gui.view.event.LoginSuccessEvent;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.ReadOnlyProperty;
import javafx.beans.property.SimpleObjectProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableMap;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.scene.input.MouseEvent;
import org.jspecify.annotations.Nullable;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.core.io.Resource;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.util.Map;

import static hust.kien.project.gui.pages.Namespace.*;

public class LoginPage implements Page {

    private final AuthService authService;
    private final ApplicationEventPublisher eventPublisher;
    private final Parent element;

    private final StringProperty username;
    private final StringProperty password;
    private final BooleanProperty rememberMe;

    @SuppressWarnings("null")
    public LoginPage(
            @Value("classpath:gui/login.fxml") Resource fxml,
            ResourceHelper helper,
            AuthService authService,
            ApplicationEventPublisher eventPublisher) {

        this.authService = authService;
        this.eventPublisher = eventPublisher;

        FXMLLoader loader = helper.load(fxml, this);
        this.element = loader.getRoot();

        ObservableMap<String, Object> namespace = loader.getNamespace();
        this.username = textField(namespace, "usernameInputField").textProperty();
        this.password = passwordField(namespace, "passwordInputField").textProperty();
        this.rememberMe = checkBox(namespace, "rememberMeCheckBox").selectedProperty();

        Credentials credentials = readFromFile();
        if (credentials != null) {
            this.username.setValue(credentials.username);
            this.password.setValue(credentials.password);
        }
    }


    private record Credentials(String username, String password) {
    }

    private static @Nullable Credentials readFromFile() {
        try {
            var lines = Files.readAllLines(new File("remember.txt").toPath());
            if (lines.size() != 2) {
                return null;
            }
            return new Credentials(lines.get(0), lines.get(1));
        } catch (IOException e) {
            return null;
        }
    }

    @FXML
    private void login(ActionEvent event) {
        String usernameInput = this.username.get();
        String passwordInput = this.password.get();

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

        if (this.rememberMe.get()) {
            // TODO: do something to remember user
        }
    }

    @FXML
    private void quenMatKhau(MouseEvent event) {
        // TODO: do something to change password
        AlertUtils.showAlert("Hãy liên hệ với quản trị viên", AlertType.INFORMATION);
    }

    @Override
    public ReadOnlyProperty<Parent> root() {
        return new SimpleObjectProperty<>(element);
    }
}
