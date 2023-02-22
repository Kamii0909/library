package hust.kien.project.controller.utils;

import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class LeftAnchorPaneProperty implements WritableValue<Double> {

    private final Node child;

    public LeftAnchorPaneProperty(Node child) {
        this.child = child;
    }

    @Override
    public Double getValue() {
        return AnchorPane.getLeftAnchor(child);
    }

    @Override
    public void setValue(Double value) {
        AnchorPane.setLeftAnchor(child, value);
    }
    
}
