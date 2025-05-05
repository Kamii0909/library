package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;

/**
 * A {@link Binding} binds the UI state to the Controller state {@code T}.
 * 
 * @param T the state that this Binding can create
 * @param I the interaction that this Binding is bound to
 */
public interface Binding<@NonNull T, @NonNull I> {
    /**
     * Called once to initialize the state (the mapping between UI and controller).
     * Any data initialization happens here.
     */
    T bind();
    
    Controller<T, I> createController();
}
