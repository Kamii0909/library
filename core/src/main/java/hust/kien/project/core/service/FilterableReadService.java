package hust.kien.project.core.service;

import java.util.List;
import java.util.stream.Stream;

public interface FilterableReadService<T, F, S> {
    List<T> find(F filter, S schema);

    default Stream<T> findStream(F filter, S schema) {
        return find(filter, schema).stream();
    }
}
