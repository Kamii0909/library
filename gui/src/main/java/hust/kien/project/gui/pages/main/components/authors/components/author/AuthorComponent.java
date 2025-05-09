package hust.kien.project.gui.pages.main.components.authors.components.author;

import org.jspecify.annotations.NonNull;

import hust.kien.project.gui.pages.Binding;
import hust.kien.project.gui.pages.Component;
import javafx.scene.layout.Region;

public class AuthorComponent implements Component<@NonNull State, @NonNull Interactions> {

    private final Region region;

    private final AuthorController authorController;

    public AuthorComponent(Region region, AuthorController authorController) {
        this.region = region;
        this.authorController = authorController;
    }

    @Override
    public @NonNull Region element() {
        return region;
    }

    @Override
    public State state() {
        return authorController.state();
    }

    @Override
    public Interactions interactions() {
        return authorController.interactions();
    }

    @Override
    public @NonNull Binding<@NonNull State, @NonNull Interactions> binding() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'binding'");
    }

}
