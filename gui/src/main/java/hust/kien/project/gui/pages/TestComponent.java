package hust.kien.project.gui.pages;

import javafx.beans.binding.Bindings;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;
import javafx.scene.Node;
import javafx.scene.layout.Region;

public class TestComponent {

    private StringProperty state;
    private BooleanProperty isLoaded;


    public void bind(ObservableList<Node> children) {


    }

    private static Region loadingRegion() {
        return new Region();
    }

    private static Region createRegion() {
        return new Region();
    }
}
