package hust.kien.project.gui.config;

import java.io.IOException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;

import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.gui.controller.component.BookComponentController;
import hust.kien.project.gui.controller.component.NewBookController;
import hust.kien.project.gui.controller.component.SelectAuthorController;
import hust.kien.project.gui.controller.component.SelectClientController;
import hust.kien.project.gui.controller.component.SelectGenreController;
import hust.kien.project.gui.view.WindowManager;
import javafx.fxml.FXMLLoader;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.AnchorPane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class BookComponentConfig {
    
    @Autowired
    private ApplicationContext ctx;
    
    @Autowired
    private LibrarianService librarianService;
    
    private WindowManager windowManager;
    
    @Bean
    public Resource bookFxml() {
        return new ClassPathResource("gui/component/book/book.fxml");
    }
    
    @Bean
    public Resource selectGenreFxml() {
        return new ClassPathResource("gui/component/book/select_genre.fxml");
    }
    
    @Bean
    public Resource selectAuthorFxml() {
        return new ClassPathResource("gui/component/book/select_author.fxml");
    }
    
    @Bean
    public Resource selectClientFxml() {
        return new ClassPathResource("gui/component/book/select_client.fxml");
    }
    
    @Bean
    public Resource newBookFxml() {
        return new ClassPathResource("gui/component/book/new_book.fxml");
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
        stage.initOwner(windowManager.getStage());
        stage.setScene(new Scene(fxmlLoader.load()));
        
        return stage;
    }
    
    @Bean
    @Scope("prototype")
    public SelectAuthorController selectAuthorController(Book book) {
        return new SelectAuthorController(book, librarianService);
    }
    
    @Bean("selectAuthorStage")
    @Scope("prototype")
    public Stage selectAuthorStage(Book book) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(selectAuthorFxml().getURL());
        
        fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz, book));
        
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initOwner(windowManager.getStage());
        stage.setScene(new Scene(fxmlLoader.load()));
        
        return stage;
    }
    
    @Bean
    @Scope("prototype")
    public SelectClientController selectClientController(Book book, boolean isBorrow) {
        return new SelectClientController(book, isBorrow);
    }
    
    @Bean("selectClientStage")
    @Scope("prototype")
    public Stage selectClientStage(Book book, boolean isBorrow) throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(selectClientFxml().getURL());
        fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz, book, isBorrow));
        
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initOwner(windowManager.getStage());
        stage.setScene(new Scene(fxmlLoader.load()));
        
        return stage;
    }
    
    @Bean
    @Scope("prototype")
    public NewBookController newbBookController() {
        return new NewBookController();
    }
    
    @Bean("newBookStage")
    @Scope("prototype")
    public Stage newBookStage() throws IOException {
        FXMLLoader fxmlLoader = new FXMLLoader(newBookFxml().getURL());
        fxmlLoader.setControllerFactory(clazz -> ctx.getBean(clazz));
        
        Stage stage = new Stage();
        stage.initModality(Modality.WINDOW_MODAL);
        stage.setAlwaysOnTop(true);
        stage.initOwner(windowManager.getStage());
        stage.setScene(new Scene(fxmlLoader.load()));
        
        return stage;
    }
}
