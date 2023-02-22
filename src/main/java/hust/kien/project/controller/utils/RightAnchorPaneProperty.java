package hust.kien.project.controller.utils;

import javafx.beans.value.WritableValue;
import javafx.scene.Node;
import javafx.scene.layout.AnchorPane;

public class RightAnchorPaneProperty implements WritableValue<Double> {

    private final Node child;

    public RightAnchorPaneProperty(Node child) {
        this.child = child;
    }

    @Override
    public Double getValue() {
        return AnchorPane.getRightAnchor(child);
    }

    @Override
    public void setValue(Double value) {
        AnchorPane.setRightAnchor(child, value);
    }

}
