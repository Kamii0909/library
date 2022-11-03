package hust.kien.project.dao;

import hust.kien.project.model.Book;
import jakarta.persistence.criteria.Predicate;

import java.util.List;

public class BookLibraryDao extends AbstractBookLibraryDao {
    public BookLibraryDao() {
    }

    @Override
    public List<Book> findByPredicate(Predicate predicate) {
        return null;
    }
}
