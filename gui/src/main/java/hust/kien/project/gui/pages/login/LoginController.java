package hust.kien.project.gui.pages.login;

import org.jspecify.annotations.NonNull;
import org.springframework.context.ApplicationEventPublisher;

import hust.kien.project.core.service.auth.AuthService;
import hust.kien.project.core.service.auth.AuthorizedContextHolder;
import hust.kien.project.core.service.auth.BadCredentialException;
import hust.kien.project.core.service.auth.NoUserFoundException;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.pages.BaseController;
import hust.kien.project.gui.view.event.LoginSuccessEvent;
import javafx.event.ActionEvent;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.input.MouseEvent;

class LoginController extends BaseController<@NonNull State, @NonNull Actions> implements Actions {
    private final AuthService authService;
    
    private final ApplicationEventPublisher eventPublisher;
    
    public LoginController(AuthService authService, ApplicationEventPublisher eventPublisher, State state) {
        super(state);
        this.authService = authService;
        this.eventPublisher = eventPublisher;
    }
    
    @Override
    public void login(ActionEvent event) {
        State state = state();
        
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
    
    @Override
    public void quenMatKhau(MouseEvent event) {
        // TODO: do something to change password
        AlertUtils.showAlert("Hãy liên hệ với quản trị viên", AlertType.INFORMATION);
    }
    
    @Override
    public Actions interactions() {
        return this;
    }
}
