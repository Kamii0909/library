package hust.kien.project.core.service;

import jakarta.persistence.criteria.CriteriaQuery;

/**
 * A service which provide CRUD operations, respecting the optional
 * {@code Filter} and {@code Schema}. The {@code Schema} is reponsible for any
 * required edges (collections).
 * 
 * @apiNote Both {@code Filter} and {@code Schema} is heavily inspired by
 *          GraphQL.
 * @implNote {@code Filter} is implemented on top of {@link CriteriaQuery}.
 *           {@code Schema} is just fetching hints.
 * @param T the entity type
 * @param F the type of {@code Filter}
 * @param S the type of {@code Schema}
 */
public interface CrudService<T, F, S> extends FilterableReadService<T, F, S> {
    T create(T entity);

    T update(T entity);

    T delete(T entity);
}
