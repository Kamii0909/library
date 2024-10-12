package hust.kien.project.gui.pages;

import javafx.scene.Parent;
import javafx.scene.Scene;

/**
 * A {@link Page} is responsible for:
 * <ul>
 * <li>Loading (initializing) a root node, possibly from FXML.</li>
 * <li>Managing the state of the displayable content.</li>
 * <li>Mapping the current state to an UI element (the root node).</li>
 * <li>Gracefully discarding states.</li>
 * </ul>
 */
public interface Page {
    /**
     * Create the {@link Parent root node} that can be used for display.
     */
    Parent show();
    
    /**
     * Called before the {@link Scene} that host this Page switch to another Page.
     * 
     * @implNote the default implementation is a no-op.
     */
    default void hide() {

    }
    
    /**
     * Called when the Page is no longer needed.
     * 
     * @implNote the default implementation is a no-op.
     */
    default void destroy() {
        
    }
    
}
