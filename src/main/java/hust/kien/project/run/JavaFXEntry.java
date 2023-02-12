package hust.kien.project.run;

import java.io.IOException;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

public class JavaFXEntry extends Application {

	@Override
	public void start(Stage primaryStage) throws Exception {
		try {
			Parent parent = FXMLLoader.load(getClass().getResource("../gui/khung.fxml"));
			Scene scene = new Scene(parent);
			scene.getStylesheets().add(getClass().getResource("../gui/style.css").toExternalForm());
			primaryStage.setScene(scene);
			// primaryStage.setResizable(false);
			// FileInputStream file = new FileInputStream("icon.png");
			// primaryStage.getIcons().add(new Image(file));
			// Screen screen = Screen.getPrimary();
			// Rectangle2D bounds = screen.getVisualBounds();
			primaryStage.show();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {
		launch(args);
	}
}
