package hust.kien.project.gui.pages;

import javafx.fxml.FXML;

/**
 * Responsible for holding fxml bindings, then reducing view logic into a
 * {@link Controller}. Controllers are responsible for managing the state,
 * synchronizing them with external sources and handling any update logic.
 */
public abstract class Fxml<T, C extends Controller<T>> {
    private final FxmlComponent<T> component;
    private C controller;
    
    protected Fxml(FxmlComponent<T> component) {
        this.component = component;
    }

    public final C controller() {
        return controller;
    }

    @FXML
    public final void initialize() {
        controller = createController();
        component.controllerCreated(controller);
    }

    public abstract C createController();
}
