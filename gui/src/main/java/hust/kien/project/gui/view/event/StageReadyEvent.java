package hust.kien.project.gui.view.event;

import org.springframework.context.ApplicationEvent;
import javafx.stage.Stage;

public class StageReadyEvent extends ApplicationEvent {
	public Stage getStage() {
		return Stage.class.cast(getSource());
	}

	public StageReadyEvent(Stage source) {
		super(source);
	}
}