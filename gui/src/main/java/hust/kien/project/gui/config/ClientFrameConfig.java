package hust.kien.project.gui.config;

import java.io.IOException;

import hust.kien.project.core.client.ClientService;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;

import hust.kien.project.gui.controller.component.NewClientController;
import hust.kien.project.gui.view.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.stage.Modality;
import javafx.stage.Stage;

@Configuration
public class ClientFrameConfig {

    private final ClientService clientService;
    private final WindowManager windowManager;

    ClientFrameConfig(ClientService clientService, WindowManager windowManager) {
        this.clientService = clientService;
        this.windowManager = windowManager;
    }

    @Bean("newClientStage")
    @Scope("prototype")
    public Stage addClientStage() throws IOException {
        FXMLLoader fxmlLoader =
                new FXMLLoader(new ClassPathResource("gui/component/client/new_client.fxml").getURL());

        fxmlLoader.setController(new NewClientController(clientService));

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initOwner(windowManager.getStage());
        stage.setScene(new Scene(fxmlLoader.load()));

        return stage;
    }
}
