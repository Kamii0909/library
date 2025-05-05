package hust.kien.project.core.book;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import hust.kien.project.core.dao.SimpleCrudRepository;
import hust.kien.project.core.model.book.Book;
import jakarta.persistence.EntityManager;

@Repository
class BookRepositoryImpl extends SimpleCrudRepository<@NonNull Book, @NonNull Long> implements BookRepository {
    
    public BookRepositoryImpl(EntityManager entityManager) {
        super(Book.class, entityManager);
    }
}
