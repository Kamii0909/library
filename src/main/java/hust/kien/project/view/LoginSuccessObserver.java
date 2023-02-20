package hust.kien.project.view;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import hust.kien.project.controller.KhungController;
import hust.kien.project.view.event.LoginSuccessEvent;

@Component
public class LoginSuccessObserver {
    private final MainFrameLoader mainFrameLoader;
    private final KhungController mainFrameController;

    @Lazy
    public LoginSuccessObserver(MainFrameLoader mainFrameLoader,
        KhungController mainFrameController) {
        this.mainFrameLoader = mainFrameLoader;
        this.mainFrameController = mainFrameController;
    }

    @EventListener
    public void listenToEvent(LoginSuccessEvent event) {
        mainFrameLoader.showMainFrame();
        mainFrameController.initializeButtonEventListener(event.getAuthenticationPrincipal());
    }
}
