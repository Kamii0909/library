package hust.kien.project;

import java.nio.file.Files;
import java.nio.file.Paths;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;
import hust.kien.project.view.WindowManager;
import hust.kien.project.view.event.StageReadyEvent;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.application.Platform;
import javafx.stage.Stage;


public class JavaFxApplication extends Application {

	private ConfigurableApplicationContext ctx;

	@Override
	public void start(Stage primaryStage) throws Exception {

		WindowManager.setStage(primaryStage);

		this.ctx.publishEvent(new StageReadyEvent(primaryStage));
	}

	@Override
	public void init() throws Exception {

		Files.createDirectories(Paths.get("./database"));

		ApplicationContextInitializer<GenericApplicationContext> initializer =
			ac -> {
				ac.registerBean(Application.class, () -> JavaFxApplication.this);
				ac.registerBean(Parameters.class, () -> getParameters());
				ac.registerBean(HostServices.class, () -> getHostServices());
			};


		this.ctx = new SpringApplicationBuilder()
			.sources(LibraryApplication.class)
			.initializers(initializer)
			.run(getParameters().getRaw().toArray(new String[0]));
	}

	@Override
	public void stop() throws Exception {
		this.ctx.close();
		Platform.exit();
	}
}
