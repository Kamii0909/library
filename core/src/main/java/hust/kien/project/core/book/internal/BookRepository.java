package hust.kien.project.core.book.internal;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import hust.kien.project.core.book.BookId;
import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;

@Repository
class BookRepository extends SimpleCrudRepository<@NonNull Book, @NonNull BookId>  {

    public BookRepository(EntityManager entityManager) {
        super(Book.class, entityManager);
    }
}
