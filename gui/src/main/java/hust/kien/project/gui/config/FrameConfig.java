package hust.kien.project.gui.config;

import java.io.IOException;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.config.FrameConfig.MainFrameConfig;
import hust.kien.project.gui.config.FrameConfig.ManagerFrameConfig;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.Region;

@Lazy
@SuppressWarnings("java:S1118")
@Import({ MainFrameConfig.class, ManagerFrameConfig.class })
public class FrameConfig {
    
    private static final Logger log = LoggerFactory.getLogger(FrameConfig.class);
    
    @Lazy
    @Configuration
    static class MainFrameConfig {
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
    
    @Lazy
    @Configuration
    static class ManagerFrameConfig {
        @Bean
        Resource managerRegionFxml() {
            return new ClassPathResource("gui/manager_frame.fxml");
        }
        
        @Bean
        @Qualifier("managerFrameRegion")
        Region managerFrameRegion(ApplicationContext ctx) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(managerRegionFxml().getURL());
            fxmlLoader.setControllerFactory(ctx::getBean);
            
            return fxmlLoader.load();
        }
        
        @Bean
        Resource employeeAccountFxml() {
            return new ClassPathResource("gui/component/manager/manage_employees.fxml");
        }
        
        @Bean
        @Qualifier("employeeAccountRegion")
        Region employeeAccountRegion(ApplicationContext ctx) throws IOException {
            FXMLLoader fxmlLoader = new FXMLLoader(employeeAccountFxml().getURL());
            fxmlLoader.setControllerFactory(ctx::getBean);
            
            return fxmlLoader.load();
        }
        
        @Bean
        Resource statisticFxml() {
            return new ClassPathResource("gui/component/manager/statistic.fxml");
        }
        
        @Bean("statisticRegion")
        Region statisticRegion(ApplicationContext ctx) throws IOException {
            log.info("Loading Resources for statistic");
            FXMLLoader fxmlLoader = new FXMLLoader(statisticFxml().getURL());
            fxmlLoader.setControllerFactory(ctx::getBean);
            
            return fxmlLoader.load();
        }
    }
    
}
