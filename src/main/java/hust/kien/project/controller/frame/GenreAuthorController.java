package hust.kien.project.controller.frame;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

@Component
public class GenreAuthorController {
    @FXML
    private GridPane mainContainer;

    private final ObjectProvider<Region> genreRegionProvider;
    private final ObjectProvider<Region> authorRegionProvider;

    public GenreAuthorController(
        @Qualifier("manageGenreRegion") ObjectProvider<Region> genreRegionProvider,
        @Qualifier("manageAuthorRegion") ObjectProvider<Region> authorRegionProvider) {
        this.genreRegionProvider = genreRegionProvider;
        this.authorRegionProvider = authorRegionProvider;
    }


    public void initialize() {
        Region genreRegion = genreRegionProvider.getObject();
        Region authorRegion = authorRegionProvider.getObject();
        mainContainer.add(genreRegion, 0, 0);
        mainContainer.add(authorRegion, 1, 0);
    }
}
