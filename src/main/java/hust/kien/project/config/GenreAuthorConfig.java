package hust.kien.project.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

@Configuration
public class GenreAuthorConfig implements BeanFactoryAware {
    
    private static final Logger log = LoggerFactory.getLogger(GenreAuthorConfig.class);
    
    private BeanFactory ctx;
    
    @Bean
    Resource genreAuthorFxml() {
        return new ClassPathResource("/gui/manage_genre_author.fxml");
    }
    
    @Bean("manageGenreAuthorRegion")
    Region manageGenreAuthorRegion() throws IOException {
        log.info("Loading resources for {}", "genre and author");
        FXMLLoader fxmlLoader = new FXMLLoader(genreAuthorFxml().getURL());
        fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz));
        
        return fxmlLoader.load();
    }
    
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.ctx = beanFactory;
    }
}
