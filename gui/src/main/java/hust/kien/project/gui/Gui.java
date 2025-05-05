package hust.kien.project.gui;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

import hust.kien.project.gui.config.BookComponentConfig;
import hust.kien.project.gui.config.ClientFrameConfig;
import hust.kien.project.gui.config.FrameConfig;
import hust.kien.project.gui.config.GenreAuthorConfig;
import hust.kien.project.gui.config.ResourceManager;
import hust.kien.project.gui.config.ResourceManager.NoProxy;
import hust.kien.project.gui.controller.ManageBookController;
import hust.kien.project.gui.controller.ManageGenreController;
import hust.kien.project.gui.controller.frame.GenreAuthorController;
import hust.kien.project.gui.controller.frame.MainFrameController;
import hust.kien.project.gui.pages.login.LoginPage;
import hust.kien.project.gui.pages.main.Introduction;
import hust.kien.project.gui.pages.main.components.authors.AuthorsComponent;
import hust.kien.project.gui.pages.main.components.authors.components.author.AuthorComponentFactory;
import hust.kien.project.gui.view.LoginSceneLoader;
import hust.kien.project.gui.view.LoginSuccessObserver;
import hust.kien.project.gui.view.MainFrameLoader;
import hust.kien.project.gui.view.StageReadyObserver;

/**
 * The JavaFX application context.
 */
@Configuration
@Import({
    LoginSceneLoader.class, StageReadyObserver.class, BookComponentConfig.class, ClientFrameConfig.class,
    MainFrameLoader.class, LoginPage.class, Introduction.class, ResourceManager.class,
    GenreAuthorConfig.class, NoProxy.class, GenreAuthorController.class, LoginSuccessObserver.class, FrameConfig.class,
    MainFrameController.class, ManageBookController.class, ManageGenreController.class, AuthorsComponent.class,
    AuthorComponentFactory.class
})
@SpringBootApplication
public class Gui {
    
}
