package hust.kien.project;

import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hust.kien.project.config.BookComponentConfig;
import hust.kien.project.config.ClientFrameConfig;
import hust.kien.project.config.FrameConfig;
import hust.kien.project.config.GenreAuthorConfig;
import hust.kien.project.config.ResourceManager;
import hust.kien.project.config.ResourceManager.NoProxy;
import hust.kien.project.controller.LoginController;
import hust.kien.project.controller.ManageAuthorController;
import hust.kien.project.controller.ManageBookController;
import hust.kien.project.controller.frame.GenreAuthorController;
import hust.kien.project.controller.frame.MainFrameController;
import hust.kien.project.view.LoginSceneLoader;
import hust.kien.project.view.LoginSuccessObserver;
import hust.kien.project.view.MainFrameLoader;
import hust.kien.project.view.StageReadyObserver;

/**
 * The JavaFX application context.
 */
@Configuration
@Import({
    LoginSceneLoader.class, StageReadyObserver.class, BookComponentConfig.class, ClientFrameConfig.class,
    MainFrameLoader.class, LoginController.class, ResourceManager.class, ManageAuthorController.class,
    GenreAuthorConfig.class, NoProxy.class, GenreAuthorController.class, LoginSuccessObserver.class, FrameConfig.class,
    MainFrameController.class, ManageBookController.class
})
public class Gui {
    
}
