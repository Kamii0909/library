package hust.kien.project.dao.bookdao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.util.List;
import org.hibernate.Hibernate;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import hust.kien.project.dao.authordao.AuthorRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.BookRentContract;

public class BookRepositoryTest {
    static SessionFactory sessionFactory;
    static Session session;
    static BookRepository bookRepository;

    @Test
    void testDelete() {
        //TODO
    }

    @Test
    void testDeleteAll() {
        assertThrows(UnsupportedOperationException.class, () -> bookRepository.deleteAll());
    }

    @Test
    void testFetch() {
        List<Book> books = bookRepository.fetch(3);

        assertEquals(3, books.size());
    }

    @Test
    void testFindByAllContractDateFrom() {
        List<Book> books =
            bookRepository.findByAllContractDateFrom(LocalDate.ofYearDay(2015, 1), LocalDate.now());

        assertNotNull(books.get(0));
    }

    @Test
    void testFindByAtLeastOneContractDateFrom() {
        List<Book> books = bookRepository.findByAtLeastOneContractDateFrom(
            LocalDate.ofYearDay(2014, 1), LocalDate.of(2015, 1, 1));

        assertEquals(1, books.size());
    }

    @Test
    void testFindByAuthor() {
        Author author = AuthorRepository.getInstance(sessionFactory).findById(1L);

        List<Book> books = bookRepository.findByAuthor(author);

        assertEquals(2, books.size());
    }

    @Test
    void testFindByBookName() {
        List<Book> books = bookRepository.findByBookName("k 2");

        assertEquals(1, books.size());
    }

    @Test
    void testFindById() {
        Book book = bookRepository.findById(1L);

        assertTrue(Hibernate.isInitialized(book));
        assertEquals("Book 0", book.getBookInfo().getBookName());
    }

    @Test
    void testFindByReleasedYear() {
        List<Book> books = bookRepository.findByReleasedYear(2001);

        assertEquals(1, books.size());
    }

    @Test
    void testFindByReleasedYearBetween() {
        List<Book> books = bookRepository.findByReleasedYearBetween(2002, 2004);

        assertEquals(3, books.size());

        for (Book book : books) {
            int i = book.getBookInfo().getReleasedYear();
            assertTrue(i >= 2002 && i <= 2004);
        }
    }

    @Test
    void testFindByStockBetween() {
        List<Book> books = bookRepository.findByStockBetween(2, 4);

        assertEquals(3, books.size());

        for (Book book : books) {
            int i = book.getBookStock().getStock();
            assertTrue(i >= 2 && i <= 4);
        }
    }

    @Test
    void testFindFromBookInfo() {
        //TODO
    }

    @Test
    void testFindFromGenre() {
        //TODO
    }

    @Test
    void testFindMatchAllGenres() {
        //TODO
    }

    @Test
    void testFindMatchAtLeastOneGenre() {
        //TODO
    }

    @Test
    void testFindWrittenByAll() {
        List<Author> authors = AuthorRepository.getInstance(sessionFactory).findByAgeBetween(22, 23);
        List<Book> books = bookRepository.findWrittenByAll(authors);

        assertEquals(1, books.size());
    }

    @Test
    void testFindWrittenByAtLeastOne() {
        List<Author> authors = AuthorRepository.getInstance(sessionFactory).findByAgeBetween(22, 23);
        List<Book> books = bookRepository.findWrittenByAtLeastOne(authors);

        assertEquals(3, books.size());
    }

    @Test
    void testSave() {
        //TODO
    }

    @Test
    void testUpdate() {
        //TODO
    }

    @Test
    void testFindAnyByAllContractDateFrom() {
        List<Book> book =
            bookRepository.findByAllContractDateFrom(LocalDate.MIN, LocalDate.now());

        assertNotNull(book.get(0));
    }

    @Test
    void testFindByReimburseCostBetween() {
        List<Book> books = bookRepository.findByReimburseCostBetween(2, 7);

        for (Book book : books) {
            double i = book.getBookStock().getReimburseCost();
            assertTrue(i >= 2 && i <= 7);
        }
    }

    @BeforeAll
    static void init() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        Metadata metadata = null;
        try {
            metadata = new MetadataSources(registry)
                .addAnnotatedClasses(Book.class, Author.class, BookGenre.class, Client.class,
                    BookRentContract.class)
                .getMetadataBuilder()
                .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                .build();
        } catch (Exception e) {
            e.printStackTrace();
        }


        assertNotNull(metadata);

        try {
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            e.printStackTrace();
            StandardServiceRegistryBuilder.destroy(registry);
        }
        bookRepository = BookRepository.getInstance(sessionFactory);

    }

    @AfterAll
    static void destroy() {
        assertNotNull(sessionFactory);
        sessionFactory.close();
    }

    @BeforeEach
    void openTransaction() {
        session = sessionFactory.getCurrentSession();
        session.beginTransaction();
    }

    @AfterEach
    void commitTransaction() {
        try {
            session.getTransaction().rollback();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }
}
