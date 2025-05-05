package hust.kien.project.core.dao;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;

public abstract class SimpleCrudRepository<@NonNull T, @NonNull ID> extends SimpleJpaRepository<T, ID>
        implements CrudRepository<T, ID> {

    private final EntityManager entityManager;

    protected SimpleCrudRepository(Class<T> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    public T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public @NonNull T merge(@NonNull T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<T> find(Specification<T> filter) {
        return findAll(filter);
    }

    @Override
    public @Nullable T find(@NonNull ID id) {
        return entityManager.find(getDomainClass(), id);
    }

    @Override
    public List<T> find(Iterable<@NonNull ID> ids) {
        return findAllById(ids);
    }

    @Override
    @SuppressWarnings("null")
    public void deleteById(@Nullable ID id) {
        super.deleteById(id);
    }

    @Override
    public void delete(@NonNull T entity) {
        super.delete(entity);
    }
}
