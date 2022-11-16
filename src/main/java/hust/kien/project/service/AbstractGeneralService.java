package hust.kien.project.service;

import java.util.List;
import java.util.function.Supplier;

public abstract class AbstractGeneralService<T> implements LibraryService<T> {

    protected abstract void beginTransaction();

    protected abstract void commitTransaction();

    protected List<T> txWrap(Supplier<List<T>> supplier) {
        beginTransaction();
        List<T> list = supplier.get();
        commitTransaction();

        return list;
    }

    protected void txWrap(Runnable runnable) {
        beginTransaction();
        runnable.run();
        commitTransaction();
    }

    
}
