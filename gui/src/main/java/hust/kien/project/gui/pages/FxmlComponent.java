package hust.kien.project.gui.pages;

import java.util.function.Supplier;

import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import javafx.scene.layout.Region;

public abstract class FxmlComponent<T> implements Component<T> {
    private final Supplier<Region> loader;
    
    private @Nullable Region element;
    
    private @Nullable Controller<T> controller;
    
    protected FxmlComponent(Resource fxml, ResourceHelper helper) {
        this.loader = () -> helper.loadFxml(fxml, this::createFxmlBinding);
    }
    
    @Override
    public final Region element() {
        if (element == null)
            element = loader.get();
        return element;
    }
    
    final <C extends Controller<T>> void controllerCreated(C controller) {
        this.controller = controller;
        binder().bind(controller.state());
    }
    
    @Override
    public final Controller<T> controller() {
        return controller;
    }
    
    public abstract Fxml<T, ? extends Controller<T>> createFxmlBinding(ApplicationContext context);
}
