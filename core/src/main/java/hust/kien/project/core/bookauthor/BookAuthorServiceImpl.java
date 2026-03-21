package hust.kien.project.core.bookauthor;

import hust.kien.project.core.author.AuthorId;
import hust.kien.project.core.author.ReadonlyAuthor;
import hust.kien.project.core.book.BookId;
import hust.kien.project.core.book.ReadonlyBook;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
class BookAuthorServiceImpl implements BookAuthorService {

    @PersistenceContext
    private final EntityManager entityManager;

    BookAuthorServiceImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    @Override
    public List<BookId> bookIdsOf(AuthorId authorId) {
        return entityManager
                .createQuery("""
                                select BA.id.bookId
                                from BookAuthor BA
                                where BA.id.authorId = :authorId""",
                        BookId.class)
                .setParameter("authorId", authorId)
                .getResultList();
    }

    @Override
    public List<AuthorId> authorIdsOf(BookId bookId) {
        return entityManager
                .createQuery("""
                                select BA.id.authorId
                                from BookAuthor BA
                                where BA.id.bookId = :bookId""",
                        AuthorId.class)
                .setParameter("bookId", bookId)
                .getResultList();
    }

    @Override
    public void setAuthorsForBook(BookId bookId, List<AuthorId> authorIds) {
        List<BookAuthor> authors = entityManager
                .createQuery("""
                                select BA
                                from BookAuthor BA
                                where BA.id.bookId = :bookId""",
                        BookAuthor.class)
                .setParameter("bookId", bookId)
                .getResultList();

        HashMap<AuthorId, BookAuthor> currentAuthors = authors.stream()
                .collect(Collectors.toMap(
                        ba -> ba.authorId(),
                        Function.identity(),
                        (a, _) -> a,
                        // Make sure it's mutable
                        HashMap::new));

        for (AuthorId authorId : new HashSet<>(authorIds)) {
            BookAuthor removed = currentAuthors.remove(authorId);

            if (removed == null) {
                // it is a new author
                BookAuthor bookAuthor = new BookAuthor(new BookAuthorLink(bookId, authorId));
                entityManager.persist(bookAuthor);
            }
        }

        // currentAuthors now contains authorId not found in requested list, deleting
        currentAuthors.forEach((_, entity) -> entityManager.remove(entity));

    }
}
