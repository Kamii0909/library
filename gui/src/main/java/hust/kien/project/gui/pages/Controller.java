package hust.kien.project.gui.pages;

import javafx.beans.Observable;

/**
 * A {@link Controller} manages the State {@code T} and response to user
 * interaction by possibly modifying the states.
 * 
 * @param T the state (likely a group of {@link Observable}s) this controller
 *          manages.
 * @param I the interactions that this controller can handle.
 */
public interface Controller<T, I> {
    T state();

    I interactions();
}
