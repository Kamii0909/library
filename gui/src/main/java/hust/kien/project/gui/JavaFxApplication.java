package hust.kien.project.gui;

import java.nio.file.Files;
import java.nio.file.Paths;

import org.springframework.boot.WebApplicationType;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.context.support.GenericApplicationContext;

import hust.kien.project.core.CoreService;
import hust.kien.project.gui.view.WindowManager;
import hust.kien.project.gui.view.event.StageReadyEvent;
import javafx.application.Application;
import javafx.application.HostServices;
import javafx.stage.Stage;

public class JavaFxApplication extends Application {
	
	private ConfigurableApplicationContext coreContext;
	
	private ConfigurableApplicationContext guiContext;
	
	@Override
	public void start(Stage primaryStage) throws Exception {
		
		String[] args = getParameters().getRaw().toArray(String[]::new);
		
		ApplicationContextInitializer<GenericApplicationContext> initializer =
				ac ->
				{
					ac.registerBean(Application.class, () -> JavaFxApplication.this);
					ac.registerBean(Parameters.class, () -> getParameters());
					ac.registerBean(HostServices.class, () -> getHostServices());
					ac.registerBean(WindowManager.class, () -> new WindowManager(primaryStage));
				};
				
		this.guiContext = new SpringApplicationBuilder(Gui.class)
				.initializers(initializer)
				.parent(coreContext)
				.web(WebApplicationType.NONE)
				.run(args);
		
		this.guiContext.publishEvent(new StageReadyEvent(primaryStage));
	}
	
	@Override
	public void init() throws Exception {
		
		// Files.createDirectories(Paths.get("./database"));
		
		String[] args = getParameters().getRaw().toArray(String[]::new);
		
		this.coreContext = new SpringApplicationBuilder(CoreService.class)
				.web(WebApplicationType.NONE)
				.run(args);
	}
	
	@Override
	public void stop() throws Exception {
		this.guiContext.close();
		this.coreContext.close();
	}
}
