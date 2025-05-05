package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;

import javafx.scene.layout.Region;

public abstract class FxmlComponent<@NonNull T, @NonNull I> implements Component<T, I> {

    private final Region region;

    protected FxmlComponent(Region region) {
        this.region = region;
    }

    @Override
    public final Region element() {
        return region;
    }

    @Override
    public Binding<@NonNull T, @NonNull I> binding() {
        throw new UnsupportedOperationException();
    }
}
