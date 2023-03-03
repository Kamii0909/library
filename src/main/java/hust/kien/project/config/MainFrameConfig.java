package hust.kien.project.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;
import lombok.extern.slf4j.Slf4j;

@Configuration
@Lazy
@Slf4j
public class MainFrameConfig {

    @Bean
    Resource mainFrameFxml() {
        return new ClassPathResource("gui/main_frame/main_frame.fxml");
    }

    @Bean
    @Qualifier("mainFrameNewRegion")
    Region mainFrameNewRegion(ApplicationContext ctx) throws IOException {
        log.info("Loading resource for main frame new");
        FXMLLoader fxmlLoader = new FXMLLoader(mainFrameFxml().getURL());
        fxmlLoader.setControllerFactory(ctx::getBean);

        return fxmlLoader.load();
    }
}
