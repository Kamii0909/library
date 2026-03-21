package hust.kien.project.gui.pages;

public abstract class BaseController<T, I> implements Controller<T, I> {
    
    private final T state;
    
    protected BaseController(T state) {
        this.state = state;
    }
    
    @Override
    public final T state() {
        return state;
    }
}
