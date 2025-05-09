package hust.kien.project.gui.view;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;

import hust.kien.project.gui.view.event.StageReadyEvent;

public class StageReadyObserver {
    private final LoginSceneLoader loader;

    public StageReadyObserver(LoginSceneLoader loader) {
        this.loader = loader;
    }

    @EventListener
    @Order(10)
    public void listen(StageReadyEvent event) {
        loader.showLoginScene();
    }
}
