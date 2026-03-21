package hust.kien.project.gui.pages;

/**
 * A {@link InterfaceComponent} is a reusable and self containing UI element, consisting
 * of:
 * <ul>
 * <li>The logic to render the user interface (the {@link javafx.scene.Node})</li>
 * <li>The states relevant to the UI</li>
 * <li>The way to map states -> UI element</li>
 * <li>Responding to user interactions</li>
 * </ul>
 */
public interface InterfaceComponent {
    /**
     * The UI element this root controls. The returned element could be
     * replaced. For example, initially displaying a Loading element, then later
     * replace it when data arrives. InterfaceComponent that use this interface
     * element must be ready to handle this switch. Doing this allows components
     * to have more freedom in choosing the resulting UI element, which is
     * a popular feature of other UI ecosystem (for example, React).
     */
    InterfaceElement element();

}
