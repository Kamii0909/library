package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import javafx.scene.layout.Region;

public abstract class FxmlComponent<@NonNull C extends FxmlComponent<C>> extends BasicComponent<C> {
    
    private final Region element;
    
    protected FxmlComponent(Resource fxml, ResourceHelper helper) {
        this.element = helper.loadFxml(fxml, this);
    }
    
    @Override
    public final Region element() {
        return element;
    }
}
