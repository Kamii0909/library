package hust.kien.project.gui.pages;

import javafx.scene.layout.Region;

/**
 * A {@link View} is reponsible for creating the layout of an UI
 * element. Note that a {@link View} has no knowledge about the states
 * it consumes or produces, it is the reposiblity of the controller.
 */
public interface View {
    /**
     * The UI element that this {@link View} is responsible for.
     */
    Region create();
}
