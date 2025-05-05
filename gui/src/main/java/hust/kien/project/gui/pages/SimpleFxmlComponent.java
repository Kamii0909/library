package hust.kien.project.gui.pages;

import org.jspecify.annotations.NonNull;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import javafx.scene.layout.Region;

public abstract class SimpleFxmlComponent<@NonNull C extends SimpleFxmlComponent<C>> extends SimpleComponent<C> {

    private final Region element;

    protected SimpleFxmlComponent(Resource fxml, ResourceHelper helper) {
        this.element = helper.loadFxml(fxml, this);
    }

    @Override
    public final Region element() {
        return element;
    }
}
