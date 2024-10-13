package hust.kien.project.gui.pages;

import javafx.beans.Observable;

/**
 * A {@link Controller} manages the State {@code T} and response to user
 * interaction by possibly modifying the states.
 * 
 * @param T the state (likely a group of {@link Observable}s) this controller
 *          manages.
 */
public interface Controller<T> {
    T state();

    static Controller<Void> noState() {
        return new Controller<Void>() {
            @Override
            public Void state() {
                throw new UnsupportedOperationException("This controller has no state");
            }
        };
    }
}
