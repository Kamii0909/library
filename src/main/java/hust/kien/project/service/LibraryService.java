package hust.kien.project.service;

import java.util.List;

public interface LibraryService<T> {
    //----------------------------
    //  ACCESSOR METHODS
    //----------------------------
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
