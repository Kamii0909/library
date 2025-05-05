package hust.kien.project.core.dao;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.repository.NoRepositoryBean;

/**
 * @param T  the entity type
 * @param ID the ID type
 * @param S  the type of {@code Schema}, which is a fetching hint.
 */
@NoRepositoryBean
public interface CrudRepository<@NonNull T, @NonNull ID> {
    T persist(T entity);

    T merge(T entity);

    void delete(T entity);

    void deleteById(ID id);

    /**
     * @param id
     * @return null if entity with {@code id} is not found.
     */
    @Nullable
    T find(ID id);

    List<T> find(Iterable<ID> ids);

    List<T> find(Specification<T> filter);
}
