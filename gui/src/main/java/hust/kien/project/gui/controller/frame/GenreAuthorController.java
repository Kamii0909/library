package hust.kien.project.gui.controller.frame;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;

import hust.kien.project.gui.pages.main.components.authors.AuthorsComponent;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

@Lazy
public class GenreAuthorController {
    @FXML
    private GridPane mainContainer;

    private final ObjectProvider<Region> genreRegionProvider;
    private final AuthorsComponent authorRegionProvider;

    public GenreAuthorController(
        @Qualifier("manageGenreRegion") ObjectProvider<Region> genreRegionProvider,
        AuthorsComponent authorRegionProvider) {
        this.genreRegionProvider = genreRegionProvider;
        this.authorRegionProvider = authorRegionProvider;
    }


    public void initialize() {
        Region genreRegion = genreRegionProvider.getObject();
        Region authorRegion = authorRegionProvider.element();
        mainContainer.add(genreRegion, 0, 0);
        mainContainer.add(authorRegion, 1, 0);
    }
}
