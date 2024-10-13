package hust.kien.project.gui.pages;

/**
 * A {@link DataBinder} binds the State {@code T} of a {@code Controller<T>} to
 * an external state source that the controller inherently has no knowledge of.
 */
@FunctionalInterface
public interface DataBinder<T> {
    /**
     * Called once for each controller.
     */
    void bind(T state);
    
    static <T> DataBinder<T> noop() {
        return state -> {};
    }
}
