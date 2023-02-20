package hust.kien.project.view.event;

import org.springframework.context.ApplicationEvent;
import hust.kien.project.service.auth.AuthorizedContextHolder;

public class LoginSuccessEvent extends ApplicationEvent {

    public AuthorizedContextHolder getAuthenticationPrincipal() {
        return ((AuthorizedContextHolder) source);
    }

    public LoginSuccessEvent(AuthorizedContextHolder employee) {
        super(employee);
    }

}
