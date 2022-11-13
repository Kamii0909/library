package hust.kien.project.dao;

import java.io.Serializable;
import java.util.List;

public interface LibraryDao<T, Id extends Serializable> {
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
