package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;

/**
 * A factory for creating multiple components. Implementation is free to choose
 * whether these components can share states, dependencies or not.
 */
public interface ComponentFactory<@NonNull T, @NonNull I, @NonNull C extends Component<T, I>, @NonNull A> {
    /**
     * Create a new component.
     */
    C createComponent(A args);
}
