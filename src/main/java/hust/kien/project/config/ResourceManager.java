package hust.kien.project.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;
import org.springframework.context.annotation.ScopedProxyMode;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

@Configuration
@Lazy
public class ResourceManager {
    
    private static final Logger log = LoggerFactory.getLogger(ResourceManager.class);
    
    @Autowired
    private ApplicationContext ctx;
    
    @Bean("defaultBookImage")
    Resource defaultBookImage() {
        return new ClassPathResource("image/default_book_image.png");
    }
    
    @Bean("avatarImage")
    Resource avatar() {
        return new ClassPathResource("image/avatar.jpg");
    }
    
    @Bean("backgroundImage")
    Resource background() {
        return new ClassPathResource("image/background.jpg");
    }
    
    @Bean("greenTickImage")
    Resource greenTick() {
        return new ClassPathResource("image/cropped-small-tick.jpg");
    }
    
    @Bean("loginBannerImage")
    Resource loginBanner() {
        return new ClassPathResource("image/login_banner.jpg");
    }
    
    @Bean("logoImage")
    Resource logo() {
        return new ClassPathResource("image/logo.png");
    }
    
    @Bean("loginFxml")
    Resource loginFxml() {
        return new ClassPathResource("gui/login.fxml");
    }
    
    @Bean("manageBookFxml")
    Resource manageBookFxml() {
        return new ClassPathResource("gui/manage_book.fxml");
    }
    
    @Bean("manageClientFxml")
    Resource manageClientFxml() {
        return new ClassPathResource("gui/manage_user.fxml");
    }
    
    @Bean("manageAuthorFxml")
    Resource manageAuthorFxml() {
        return new ClassPathResource("gui/manage_author_new.fxml");
    }
    
    @Bean("manageGenreFxml")
    Resource manageGenreFxml() {
        return new ClassPathResource("gui/manage_genre_new.fxml");
    }
    
    @Bean("manageTicketFxml")
    Resource manageTicketFxml() {
        return new ClassPathResource("gui/manage_ticket.fxml");
    }
    
    @Bean("userInformationFxml")
    Resource userInformationFxml() {
        return new ClassPathResource("gui/user_information.fxml");
    }
    
    @Bean("introductionPageFxml")
    Resource introductionPageFxml() {
        return new ClassPathResource("gui/introduction.fxml");
    }
    
    @Lazy
    @Configuration(proxyBeanMethods = false)
    public class NoProxy {
        @Bean("loginRegion")
        Region loginRegion(@Qualifier("loginFxml") Resource resource) {
            return getRegion(resource, "login");
        }
        
        @Bean("manageBookRegion")
        @Scope(proxyMode = ScopedProxyMode.NO)
        Region manageBookRegion(@Qualifier("manageBookFxml") Resource resource) {
            return getRegion(resource, "manage book");
        }
        
        @Bean("manageClientRegion")
        Region manageClientRegion(@Qualifier("manageClientFxml") Resource resource) {
            return getRegion(resource, "manage client");
        }
        
        @Bean("manageAuthorRegion")
        Region manageAuthorRegion(@Qualifier("manageAuthorFxml") Resource resource) {
            return getRegion(resource, "manage author");
        }
        
        @Bean("manageGenreRegion")
        Region manageGenreRegion(@Qualifier("manageGenreFxml") Resource resource) {
            return getRegion(resource, "manage genre");
        }
        
        @Bean("manageTicketRegion")
        Region manageTicketRegion(@Qualifier("manageTicketFxml") Resource resource) {
            return getRegion(resource, "manage ticket");
        }
        
        @Bean("userInformationRegion")
        Region userInformationRegion(@Qualifier("userInformationFxml") Resource resource) {
            return getRegion(resource, "user information");
        }
        
        @Bean("introductionPageRegion")
        Region introductionPageRegion(@Qualifier("introductionPageFxml") Resource resource) {
            return getRegion(resource, "introduction");
        }
        
        Region getRegion(Resource resource, String resourceName) {
            try {
                log.info("Loading resource for {} ", resourceName);
                
                FXMLLoader fxmlLoader = new FXMLLoader(resource.getURL());
                fxmlLoader.setControllerFactory(ctx::getBean);
                return fxmlLoader.load();
            } catch (IOException e) {
                throw new IllegalStateException("Can't load fxml for " + resourceName, e);
            }
        }
    }
}
