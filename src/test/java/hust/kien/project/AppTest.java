package hust.kien.project;

import hust.kien.project.model.*;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.*;

import java.util.Collection;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class AppTest {
    static SessionFactory sessionFactory;
    static Session session;

    @BeforeAll
    static void init() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        Metadata metadata = new MetadataSources(registry)
            .addAnnotatedClasses(Book.class, Author.class, BookGenre.class)
            .getMetadataBuilder()
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
            .build();

        try {
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @AfterAll
    static void destroy() {
        sessionFactory.close();
    }

    @Test
    @Order(1)
    void preloadData() {
        Author author1 = new Author(new AuthorInfo("Author 1", 22));
        Author author2 = new Author(new AuthorInfo("Author 2", 23));

        Book book1 = new Book(new BookInfo("Book 1", 2014));
        Book book2 = new Book(new BookInfo("Book 2", 2020));

        book1.getBookInfo().getAuthors().addAll(List.of(author1, author2));
        book2.getBookInfo().getAuthors().addAll(List.of(author2));

        session.persist(book1);
        session.persist(book2);
    }

    @Test
    @Order(2)
    void testAddAuthorToBookAndCascadePersist() {
        Author author = new Author(new AuthorInfo("Author 3", 24));

        Book book = session
            .createQuery("from Book b where b.bookInfo.bookName like :bookHint", Book.class)
            .setParameter("bookHint", "%1")
            .getSingleResult();

        assertNotNull(book, "Can't find Book 1");

        book.getBookInfo().getAuthors().add(author);

        session.persist(book);
    }

    @Test
    @Order(3)
    void testGetAddedAuthorFromBook() {
        Collection<Author> authors = session
            .createQuery("from Book b where b.bookInfo.bookName like :bookHint ", Book.class)
            .setParameter("bookHint", "%1")
            .getSingleResult()
            .getBookInfo()
            .getAuthors();
        assertEquals(3, authors.size());
    }

    @Test
    @Order(4)
    void testAutoUpdate() {
        Author author2 = session
            .createQuery("from Author a where a.authorInfo.age = 23", Author.class)
            .getSingleResult();
        assertEquals(23, author2.getAuthorInfo().getAge());
        author2.getAuthorInfo().setAge(27);
        assertEquals(27, author2.getAuthorInfo().getAge());
    }

    @Test
    @Order(5)
    void testGetAutoUpdatedAuthor() {
        Author author2 = session
            .createQuery("from Author a where a.authorInfo.age = 27", Author.class)
            .getSingleResult();

        assertNotNull(author2);
    }

    @Test
    @Order(5)
    void testGetBookFromAuthor() {
        Book book = session
            .createQuery("select b from Book b join b.bookInfo.authors a" + " where a.authorInfo.name like '%3'",
                         Book.class)
            .getSingleResult();
        assertNotNull(book);
        assertEquals("Book 1", book.getBookInfo().getBookName());
    }

    //@Test
    @Order(6)
    void testGetBookFromAuthors() {
        Author author3 = session.getReference(Author.class, 3L);
        Author author2 = session.getReference(Author.class, 2L);
        Author author1 = session.getReference(Author.class, 1L);

        List<Book> booksWrittenByAuthor2And3 = session
            .createQuery("select b from Book b join Author a on b.bookInfo.authors = :authors", Book.class)
            .setParameterList("authors", List.of(author1, author2, author3))
            .list();
        System.out.println(booksWrittenByAuthor2And3);
    }


    @Test
    @Order(7)
    void testCriteria() {
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        List<Author> authors = session.createQuery("from Author a where a.id in (1,2,3)", Author.class).getResultList();

        for (Author author : authors) {
            cq.where(cb.isMember(author, root.get(Book_.bookInfo).get(BookInfo_.authors)));
        }

        TypedQuery<Book> tq = session.createQuery(cq);
        assertEquals(1, tq.getSingleResult().getBookId());
        assertNotNull(tq.getSingleResult());
    }

    @BeforeEach
    void openTransaction() {
        session = sessionFactory.openSession();
        session.beginTransaction();
    }

    @AfterEach
    void commitTransaction() {
        try {
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
        }
    }


}
