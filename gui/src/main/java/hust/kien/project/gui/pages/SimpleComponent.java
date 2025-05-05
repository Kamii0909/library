package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;

/**
 * A component that uses itself as the state and interactions.
 */
public abstract class SimpleComponent<@NonNull C extends SimpleComponent<C>>
        implements Component<C, C>, Binding<C, C> {

    @Override
    public final @NonNull C state() {
        return cast(this);
    }

    @Override
    public final @NonNull C interactions() {
        return cast(this);
    }

    @Override
    public final Binding<@NonNull C, @NonNull C> binding() {
        return this;
    }

    @Override
    public final @NonNull C bind() {
        return cast(this);
    }

    @Override
    public final Controller<@NonNull C, @NonNull C> createController() {
        return cast(this);
    }

    @SuppressWarnings("unchecked")
    private static <@NonNull C extends SimpleComponent<C>> C cast(SimpleComponent<C> component) {
        return (C) component;
    }
}
