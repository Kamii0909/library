package hust.kien.project.view;

import org.springframework.context.event.EventListener;
import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;
import hust.kien.project.view.event.StageReadyEvent;

@Component
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
