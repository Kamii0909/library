package hust.kien.project.core.dao;

import java.io.Serializable;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

public class LibraryRepositoryImpl<T, ID extends Serializable> extends SimpleJpaRepository<T, ID>
    implements LibraryRepository<T, ID> {

    @SuppressWarnings("unused")
    private final EntityManager entityManager;

    public LibraryRepositoryImpl(JpaEntityInformation<T, ?> entityInformation,
        EntityManager entityManager) {
        super(entityInformation, entityManager);
        this.entityManager = entityManager;
    }

    public LibraryRepositoryImpl(Class<T> domainClass, EntityManager em) {
        super(domainClass, em);
        this.entityManager = em;
    }

    @Override
    public List<T> findAll(Specification<T> spec, long offset, int maxResults, Sort sort) {
        TypedQuery<T> query = getQuery(spec, sort);

        if (offset < 0) {
            throw new IllegalArgumentException("Offset must not be less than zero!");
        }
        if (maxResults < 1) {
            throw new IllegalArgumentException("Max results must not be less than one!");
        }

        query.setFirstResult((int) offset);
        query.setMaxResults(maxResults);
        return query.getResultList();

    }

    @Override
    public List<T> findAll(Specification<T> spec, long offset, int maxResults) {
        return findAll(spec, offset, maxResults, Sort.unsorted());
    }

}
