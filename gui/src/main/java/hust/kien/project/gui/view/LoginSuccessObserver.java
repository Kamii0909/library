package hust.kien.project.gui.view;

import org.springframework.context.annotation.Lazy;
import org.springframework.context.event.EventListener;

import hust.kien.project.gui.controller.frame.MainFrameController;
import hust.kien.project.gui.view.event.LoginSuccessEvent;

public class LoginSuccessObserver {
    private final MainFrameLoader mainFrameLoader;
    private final MainFrameController mainFrameController;

    @Lazy
    public LoginSuccessObserver(MainFrameLoader mainFrameLoader,
        MainFrameController mainFrameController) {
        this.mainFrameLoader = mainFrameLoader;
        this.mainFrameController = mainFrameController;
    }

    @EventListener
    public void listenToEvent(LoginSuccessEvent event) {
        mainFrameLoader.showMainFrame();
        mainFrameController.initializeButtonEventListener(event.getAuthenticationPrincipal());
    }
}
