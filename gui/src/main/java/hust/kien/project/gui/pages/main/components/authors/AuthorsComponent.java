package hust.kien.project.gui.pages.main.components.authors;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import hust.kien.project.core.author.AuthorService;
import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.Component;
import hust.kien.project.gui.pages.main.components.authors.components.author.AuthorComponentFactory;
import javafx.scene.layout.Region;

public class AuthorsComponent implements Component<@NonNull State, @NonNull AuthorsInteractions> {

    private final AuthorService authorService;
    private final Region region;

    private AuthorsInteractions controller;
    private State state;

    @SuppressWarnings("null")
    AuthorsComponent(
            @Value("classpath:gui/manage_author_new.fxml") Resource fxml,
            ResourceHelper helper,
            AuthorService authorService,
            AuthorComponentFactory factory) {
        this.authorService = authorService;
        this.region = helper.loadFxml(fxml, new Fxml(this, factory));
    }

    @Override
    public Region element() {
        return region;
    }

    void setState(State state) {
        this.state = state;
        this.controller = new AuthorsInteractions(state, authorService);
    }

    @Override
    public State state() {
        return state;
    }

    @Override
    public AuthorsInteractions interactions() {
        return controller;
    }

}
