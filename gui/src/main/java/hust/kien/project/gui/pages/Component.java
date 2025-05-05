package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;

import javafx.scene.layout.Region;

/**
 * A {@link Component} is a reusable and self containing UI element, consisting
 * of:
 * <ul>
 * <li>The logic to render the user interface (the {@link Region})</li>
 * <li>The states relevant to the UI</li>
 * <li>The way to map states -> UI element</li>
 * <li>Responding to user interactions</li>
 * </ul>
 * The flow of creating a {@link Component}:
 * <ul>
 * <li>Create the {@link #element() view}
 * <li>Create the {@link #controller() controller}, which also initialize the
 * state.
 * <li>Bind the controller to an external state, if required.
 * </ul>
 * 
 * @param T the states that this {@link Component} cares about.
 * @param I the user interaction this {@link Component} can handle.
 */
public interface Component<@NonNull T, @NonNull I> {
    /**
     * The UI element this component controls.
     */
    Region element();
    
    /**
     * The object reponsible for managing the states and the user interactions.
     */
    Controller<T, I> controller();
    
    /**
     * The object responsible for UI <-> state mapping
     */
    Binding<T, I> binding();
    
}
