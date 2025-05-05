package hust.kien.project.core.author;

import java.util.List;
import java.util.stream.Stream;

import org.jspecify.annotations.NonNull;

import hust.kien.project.core.service.CrudService;
import hust.kien.project.core.service.VoidSchema;

public interface AuthorService extends CrudService<@NonNull Author, @NonNull AuthorFilter, @NonNull VoidSchema> {
    
    List<@NonNull Author> find(AuthorFilter filter);
    
    default Stream<@NonNull Author> findStream(AuthorFilter filter) {
        return findStream(filter, VoidSchema.get());
    }
    
    @Override
    default List<@NonNull Author> find(AuthorFilter filter, VoidSchema schema) {
        return find(filter);
    }
}
