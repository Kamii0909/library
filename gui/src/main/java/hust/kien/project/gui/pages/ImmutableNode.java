package hust.kien.project.gui.pages;

import javafx.beans.InvalidationListener;
import javafx.beans.value.ChangeListener;
import javafx.scene.Node;


/**
 * Interface root
 */
public final class ImmutableNode implements InterfaceElement {
    private final Node node;

    public ImmutableNode(Node node) {
        this.node = node;
    }

    @Override
    public Object getBean() {
        throw new UnsupportedOperationException("Is this needed?");
    }

    @Override
    public String getName() {
        return node.getClass().getName();
    }

    @Override
    public void addListener(ChangeListener<? super Node> listener) {

    }

    @Override
    public void removeListener(ChangeListener<? super Node> listener) {

    }

    @Override
    public Node getValue() {
        return node;
    }

    @Override
    public void addListener(InvalidationListener listener) {

    }

    @Override
    public void removeListener(InvalidationListener listener) {

    }
}
