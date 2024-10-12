package hust.kien.project.gui.common;

import java.io.IOException;
import java.net.URL;

import org.springframework.beans.factory.BeanFactory;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Component;

import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

@Component
public final class ResourceHelper {
    
    private BeanFactory beanFactory;
    
    private ResourceHelper(BeanFactory beanFactory) {
        this.beanFactory = beanFactory;
    }
    
    public Region loadFxml(Resource fxml) {
        URL url;
        try {
            url = fxml.getURL();
        } catch (IOException e) {
            throw new AssertionError("Resource" + fxml + " not available", e);
        }
        
        FXMLLoader loader = new FXMLLoader(url);
        loader.setControllerFactory(beanFactory::getBean);
        
        try {
            return loader.load();
        } catch (IOException e) {
            throw new AssertionError("Invalid fxml from " + fxml, e);
        }
    }
    
}
