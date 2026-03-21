package hust.kien.project.gui.pages;

/**
 * A factory for creating multiple components. Implementation is free to choose
 * whether these components can share states, dependencies or not.
 */
public interface ComponentFactory<C extends InterfaceComponent, A> {
    /**
     * Create a new root.
     */
    C createComponent(A args);
}
