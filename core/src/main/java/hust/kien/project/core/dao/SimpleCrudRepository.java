package hust.kien.project.core.dao;

import java.util.List;

import com.google.errorprone.annotations.CanIgnoreReturnValue;
import org.jspecify.annotations.Nullable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;

import jakarta.persistence.EntityManager;

public abstract class SimpleCrudRepository<T, ID> extends SimpleJpaRepository<T, ID>
        implements CrudRepository<T, ID> {

    protected final EntityManager entityManager;

    protected SimpleCrudRepository(Class<T> entityClass, EntityManager entityManager) {
        super(entityClass, entityManager);
        this.entityManager = entityManager;
    }

    @Override
    @CanIgnoreReturnValue
    public T persist(T entity) {
        entityManager.persist(entity);
        return entity;
    }

    @Override
    public T merge(T entity) {
        return entityManager.merge(entity);
    }

    @Override
    public List<T> find(Specification<T> filter) {
        return findAll(filter);
    }

    @Override
    public @Nullable T find(ID id) {
        return entityManager.find(getDomainClass(), id);
    }

    @Override
    public List<T> find(Iterable<ID> ids) {
        return findAllById(ids);
    }

    @Override
    public void deleteById(@Nullable ID id) {
        if (id == null) return;
        super.deleteById(id);
    }

    @Override
    public void delete(T entity) {
        super.delete(entity);
    }
}
