package hust.kien.project.dao;

import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.Session;
import org.hibernate.Transaction;

import java.io.Serializable;
import java.util.List;

public interface LibraryDao<T, Id extends Serializable> {
    //----------------------------
    //  TRANSACTIONAL METHODS
    //----------------------------
    public Session getCurrentSession();

    public Transaction beginTransaction();
    public void commitTransaction();

    //----------------------------
    //  ACCESSOR METHODS
    //----------------------------
    public T findById(Id id);
    public List<T> fetch(int amount);


    //----------------------------
    //  MUTATOR METHODS
    //----------------------------

    //---------- SAVE ------------
    public void save(T entity);

    //--------- DELETE -----------
    public void delete(T entity);
    public void deleteAll();

    //--------- UPDATE -----------
    public void update(T entity);
}
