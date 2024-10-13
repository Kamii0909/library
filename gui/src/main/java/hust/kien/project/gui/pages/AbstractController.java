package hust.kien.project.gui.pages;

public abstract class AbstractController<T> implements Controller<T> {
    
    private final T state;
    
    protected AbstractController(T state) {
        this.state = state;
    }
    
    @Override
    public final T state() {
        return state;
    }
}
