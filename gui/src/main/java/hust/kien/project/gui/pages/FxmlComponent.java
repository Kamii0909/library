package hust.kien.project.gui.pages;

import java.util.Objects;
import java.util.function.Supplier;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import com.google.common.base.Preconditions;

import hust.kien.project.gui.common.ResourceHelper;
import javafx.scene.layout.Region;

public abstract class FxmlComponent<@NonNull T, @NonNull I> implements Component<T, I> {
    private final Supplier<@NonNull Region> loader;
    
    private @Nullable Region element;
    
    private @Nullable Controller<T, I> controller;
    
    private @Nullable FxmlBinding<T, I> binding;
    
    protected FxmlComponent(Resource fxml, ResourceHelper helper) {
        this.loader = () -> helper.loadFxml(fxml, this::doCreateFxmlBinding);
    }
    
    @Override
    public final Region element() {
        Region element = this.element;
        if (element == null) {
            @SuppressWarnings("null")
            @NonNull Region region = loader.get();
            element = region;
            return region;
        }
        return element;
    }
    
    final void bindingIntialized() {
        Preconditions.checkState(this.binding != null, "This component is not bound");
        Preconditions.checkState(this.controller == null, "This component controller is already created");
        this.controller = binding.createController();
    }
    
    @Override
    public final @NonNull Controller<T, I> controller() {
        return Objects.requireNonNull(controller, "This component is not initialized.");
    }
    
    @Override
    public final FxmlBinding<T, I> binding() {
        return Objects.requireNonNull(binding, "This component is not bound");
    }
    
    private FxmlBinding<T, I> doCreateFxmlBinding(ApplicationContext context) {
        FxmlBinding<T, I> binding = createFxmlBinding(context);
        this.binding = binding;
        return binding;
    }
    
    public abstract FxmlBinding<T, I> createFxmlBinding(ApplicationContext context);
}
