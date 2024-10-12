package hust.kien.project.gui.config;

import java.io.IOException;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import hust.kien.project.core.model.author.Author;
import hust.kien.project.gui.controller.component.AuthorComponentController;
import javafx.fxml.FXMLLoader;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;

@Configuration
public class AuthorComponentConfig implements ApplicationContextAware {

    private ApplicationContext ctx;

    @Bean
    public Resource authorComponentFxml() {
        return new ClassPathResource("gui/component/author/author.fxml");
    }

    @Bean
    @Scope("prototype")
    public AuthorComponentController authorComponentController(Author author, Image image) {
        return new AuthorComponentController(author, image);
    }


    @Bean("authorComponentRegion")
    @Scope("prototype")
    public Region authorRegion(Author author, Image image) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(authorComponentFxml().getURL());
        fxmlLoader.setControllerFactory(clz -> ctx.getBean(clz, author, image));

        return fxmlLoader.load();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.ctx = applicationContext;
    }
}
