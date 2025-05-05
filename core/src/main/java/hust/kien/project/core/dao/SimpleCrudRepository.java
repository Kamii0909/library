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
    public @NonNull T persist(@NonNull T entity) {
        entityManager.persist(entity);
        return entity;
    }
    
    @Override
    public @NonNull T merge(@NonNull T entity) {
        return entityManager.merge(entity);
    }
    
    @Override
    public List<@NonNull T> find(Specification<@NonNull T> filter) {
        return findAll(filter);
    }
    
    @Override
    public @Nullable T find(@NonNull ID id) {
        return entityManager.find(getDomainClass(), id);
    }
    
    @Override
    public List<@NonNull T> find(Iterable<@NonNull ID> ids) {
        return findAllById(ids);
    }
}
