package hust.kien.project.core.author;

import hust.kien.project.core.book.BookId;
import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
class AuthorRepository extends SimpleCrudRepository<Author, Long> {

    public AuthorRepository(EntityManager entityManager) {
        super(Author.class, entityManager);
    }

    List<Author> authorsOf(BookId bookId) {
        return entityManager.createQuery(
                        """
                                from Author
                                where id in (select BA.id.authorId
                                             from BookAuthor BA
                                             where BA.id.bookId = :bookId)""", Author.class)
                .setParameter("bookId", bookId.id())
                .getResultList();
    }
}
