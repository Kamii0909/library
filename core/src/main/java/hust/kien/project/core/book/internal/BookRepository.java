package hust.kien.project.core.book.internal;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.bookauthor.BookAuthor_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.stereotype.Repository;

import hust.kien.project.core.book.BookId;
import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;

import java.util.List;

@Repository
class BookRepository extends SimpleCrudRepository<Book, Long> {

    public BookRepository(EntityManager entityManager) {
        super(Book.class, entityManager);
    }

    List<Book> booksOf(AuthorId authorId) {
        return entityManager.createQuery(
                        """
                                from Book
                                where id in (select BA.id.bookId
                                             from BookAuthor BA
                                             where BA.id.authorId = :authorId)""", Book.class)
                .setParameter("authorId", authorId.id())
                .getResultList();
    }

    List<String> genresOf(Long bookId) {
        return entityManager
                .unwrap(Session.class)
                .createNativeQuery("select B.genre from Book_BookGenre B where B.book_id = :bookId", String.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }
}
