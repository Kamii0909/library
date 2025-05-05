package hust.kien.project.gui.common;

import java.io.IOException;
import java.net.URL;

import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

@Component
public final class ResourceHelper {

    public Region loadFxml(Resource fxml, Object controller) {
        URL url;
        try {
            url = fxml.getURL();
        } catch (IOException e) {
            throw new AssertionError("Resource" + fxml + " not available", e);
        }

        FXMLLoader loader = new FXMLLoader(url);
        loader.setController(controller);

        try {
            return loader.load();
        } catch (IOException e) {
            throw new AssertionError("Invalid fxml from " + fxml, e);
        }
    }

}
