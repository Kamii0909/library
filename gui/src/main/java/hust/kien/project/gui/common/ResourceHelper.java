package hust.kien.project.gui.common;

import java.io.IOException;
import java.net.URL;
import java.util.function.Function;

import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import hust.kien.project.gui.pages.Fxml;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

@Component
public final class ResourceHelper {
    
    private ApplicationContext context;
    
    private ResourceHelper(ApplicationContext beanFactory) {
        this.context = beanFactory;
    }
    
    public Region loadFxml(Resource fxml, Function<ApplicationContext, Fxml<?, ?>> controllerProvider) {
        URL url;
        try {
            url = fxml.getURL();
        } catch (IOException e) {
            throw new AssertionError("Resource" + fxml + " not available", e);
        }
        
        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(ignored -> controllerProvider.apply(context));
        
        try {
            return loader.load();
        } catch (IOException e) {
            throw new AssertionError("Invalid fxml from " + fxml, e);
        }
    }
    
}
