package hust.kien.project.config;

import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import hust.kien.project.controller.component.BookComponentController;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;
import javafx.fxml.FXMLLoader;
import javafx.scene.layout.AnchorPane;

@Configuration
public class BookComponentConfig {

    @SuppressWarnings("java:S3305")
    @Autowired
    private LibrarianService librarianService;

    @Bean
    public Resource bookFxml() {
        return new ClassPathResource("gui/component/book/book.fxml");
    }

    @Bean("bookComponentRegion")
    public AnchorPane bookComponentRegion(Book book) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(bookFxml().getURL());

        fxmlLoader.setControllerFactory(clazz -> bookComponentController(book));

        return fxmlLoader.load();
    }

    @Bean
    @Scope("prototype")
    public BookComponentController bookComponentController(Book book) {
        return new BookComponentController(book, librarianService);
    }
}
