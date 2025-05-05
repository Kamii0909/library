package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;

import javafx.fxml.FXML;

/**
 * Responsible for holding fxml bindings, then reducing view logic into a
 * {@link Controller}. Controllers are responsible for managing the state,
 * synchronizing them with external sources and handling any update logic.
 */
public abstract class FxmlBinding<@NonNull T, @NonNull I> implements Binding<T, I> {
    private final FxmlComponent<T, I> component;
    
    protected <C extends FxmlComponent<T, I>> FxmlBinding(@NonNull C component) {
        this.component = component;
    }
    
    @FXML
    public final void initialize() {
        component.bindingIntialized();
    }
    
    public final FxmlComponent<T, I> component() {
        return component;
    }
    
    public final I handler() {
        return component.controller().interactions();
    }
    
    public abstract @NonNull Controller<T, I> createController();
}
