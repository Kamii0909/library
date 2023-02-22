package hust.kien.project.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import hust.kien.project.controller.component.BookComponentController;
import hust.kien.project.controller.component.SelectGenreController;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.view.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

@SuppressWarnings("java:S3305")
@Configuration
public class BookComponentConfig {

    @Autowired
    private ApplicationContext ctx;

    @Autowired
    private LibrarianService librarianService;

    @Bean
    public Resource bookFxml() {
        return new ClassPathResource("gui/component/book/book.fxml");
    }

    @Bean
    public Resource selectGenreFxml() {
        return new ClassPathResource("gui/component/book/select_genre.fxml");
    }

    @Bean("bookComponentRegion")
    @Scope("prototype")
    public AnchorPane bookComponentRegion(Book book, Image image) throws IOException {

        FXMLLoader fxmlLoader = new FXMLLoader(bookFxml().getURL());
        fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz, book, image));

        return fxmlLoader.load();
    }

    @Bean
    @Scope("prototype")
    public BookComponentController bookComponentController(Book book, Image image) {
        return new BookComponentController(book, image);
    }

    @Bean
    @Scope("prototype")
    public SelectGenreController selectGenreController(Book book) {
        return new SelectGenreController(book, librarianService);
    }

    @Bean("selectGenreStage")
    @Scope("prototype")
    public Stage selectGenreStage(Book book) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(selectGenreFxml().getURL());

        fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz, book));

        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initOwner(WindowManager.getStage());
        stage.setScene(new Scene(fxmlLoader.load()));

        return stage;

    }
}
