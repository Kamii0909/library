package hust.kien.project.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;

public abstract class LibraryCriteriaBuilder<T> {
    protected CriteriaBuilder cb;

    protected CriteriaQuery<T> cq;

    protected Root<T> root;

    public LibraryCriteriaBuilder(Class<T> clazz, CriteriaBuilder cb) {
        this.cb = cb;
        cq = cb.createQuery(clazz);
        root = cq.from(clazz);
    }

    public LibraryCriteriaBuilder<T> where(Predicate[] predicates) {
        cq.where(predicates);
        return this;
    }

    public CriteriaQuery<T> getQuery() {
        return cq;
    }

}
