package hust.kien.project;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertTrue;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Date;
import java.util.List;
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
import org.junit.jupiter.api.MethodOrderer;
import org.junit.jupiter.api.Order;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestMethodOrder;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;
import hust.kien.project.model.book.BookStock;
import hust.kien.project.model.book.BookStock_;
import hust.kien.project.model.book.Book_;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientContactInfo;
import hust.kien.project.model.client.ClientTier;
import hust.kien.project.model.rent.BookRentContract;
import hust.kien.project.model.rent.BookRentContract_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import jakarta.persistence.criteria.Subquery;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class IntegrationTest {
    static SessionFactory sessionFactory;
    static Session session;


    // ---- Assertion Tests -----
    @Test
    @Order(3)
    void verifyAddAuthorsToBooks() {
        List<Author> authors =
            session.createQuery("from Author a", Author.class).getResultList();

        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();

        assertEquals(5, authors.size());
        assertEquals(5, books.size());

        assertEquals(2, authors.get(0).getAuthorInfo().getBooks().size());
        assertEquals(2, books.get(0).getBookInfo().getAuthors().size());

        assertEquals("Book 0", books.get(0).getBookInfo().getBookName());

        assertNotNull(books.stream()
            .filter(book -> book.getBookInfo().getBookName().equals("Author 1"))
            .findAny());
    }

    @Test
    @Order(5)
    void verifyAddGenresToBooks() {
        List<BookGenre> genres = session
            .createQuery("from BookGenre bg", BookGenre.class).getResultList();

        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();

        assertEquals(5, genres.size());
        assertEquals(5, books.size());

        assertEquals("Book 0", books.get(0).getBookInfo().getBookName());

        assertNotNull(books.stream().filter(book -> book.getBookInfo()
            .getBookGenres().contains(new BookGenre("Book Genre"))).findAny());
    }

    @Test
    @Order(7)
    void verifyAddStockToBooks() {
        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();

        assertEquals(5, books.size());

        assertEquals(3, books.get(0).getBookStock().getReimburseCost());
    }

    @Test
    @Order(9)
    void verifyAddContract() {
        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();

        int i = books.get(0).getBookStock().getCompletedContracts().size();
        assertEquals(2, i);

        int j = books.get(0).getBookStock().getOngoingContracts().size();
        assertEquals(1, j);

        BookRentContract contract = books.get(2).getBookStock()
            .getOngoingContracts().stream().findAny().get();

        assertTrue(contract.getStartDate().isAfter(LocalDate.of(2015, 1, 1)));
        assertTrue(contract.isActive());
    }

    @Test
    @Order(10)
    void complexQuery() {
        // find all Books with all completed contracts start after 2016
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();

        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        Subquery<BookRentContract> sq = cq.subquery(BookRentContract.class);
        Root<BookRentContract> subRoot = sq.from(BookRentContract.class);



        sq.select(subRoot).where(
            cb.lessThan(subRoot.get(BookRentContract_.startDate),
                LocalDate.of(2016, 2, 1)),
            cb.equal(root, subRoot.get(BookRentContract_.book)));

        cq.where(cb.not(cb.exists(sq)));

        List<Book> books = session.createQuery(cq).getResultList();

        for (Book book : books) {
            System.out.println(book.getBookInfo().getBookName());
        }

        assertEquals(3, books.size());
    }


    // ---- Mutation Tests -----
    @Test
    @Order(2)
    void addAuthorsToBooks() {
        List<Author> authors =
            session.createQuery("from Author a", Author.class).getResultList();
        authors.sort((a1, a2) -> a1.getAuthorInfo().getName()
            .compareTo(a2.getAuthorInfo().getName()));

        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();
        books.sort((b1, b2) -> b1.getBookInfo().getBookName()
            .compareTo(b2.getBookInfo().getBookName()));

        assertEquals(5, authors.size());
        assertEquals(5, books.size());

        int[][] params = {{0, 1}, {2, 3}, {4, 0}, {1, 2}, {3, 4}};

        for (int i = 0; i < params.length; i++) {
            for (int j : params[i]) {
                books.get(i).getBookInfo().getAuthors().add(authors.get(j));
            }
        }
    }

    @Test
    @Order(4)
    void addBookGenresToBooks() {
        List<BookGenre> genres = session
            .createQuery("from BookGenre bg", BookGenre.class).getResultList();
        genres.sort((bg1, bg2) -> bg1.getName().compareTo(bg2.getName()));

        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();
        books.sort((b1, b2) -> b1.getBookInfo().getBookName()
            .compareTo(b2.getBookInfo().getBookName()));

        assertEquals(5, genres.size());
        assertEquals(5, books.size());

        int[][] params = {{0, 1}, {2, 3}, {4, 0}, {1, 2}, {3, 4}};

        for (int i = 0; i < params.length; i++) {
            for (int j : params[i]) {
                books.get(i).getBookInfo().getBookGenres().add(genres.get(j));
            }
        }
    }

    @Test
    @Order(6)
    void changeBookStockInfo() {
        List<Book> books =
            session.createQuery("from Book b", Book.class).getResultList();

        books.sort((b1, b2) -> b1.getBookInfo().getBookName()
            .compareTo(b2.getBookInfo().getBookName()));

        assertEquals(5, books.size());

        for (int i = 0; i < books.size(); i++) {
            books.get(i).setBookStock(new BookStock(i + 1, 2 * i + 3));
        }
    }

    @Test
    @Order(8)
    void testAddContracts() {

        List<Book> books = session
            .createSelectionQuery("from Book b", Book.class).getResultList();

        List<Client> clients =
            session.createSelectionQuery("from Client c", Client.class)
                .getResultList();

        assertEquals(5, books.size());
        assertEquals(5, clients.size());

        int[][] idC = {{1, 3}, {2, 4}, {3, 0}, {4, 1}, {0, 2}};
        int[][] idO = {{1, 4}, {2, 0}, {3, 1}, {4, 2}, {0, 3}};

        BookRentContract[] completed = new BookRentContract[idC.length + 5];
        BookRentContract[] ongoing = new BookRentContract[idO.length + 5];

        assertEquals(idC.length, idO.length);

        session.getTransaction().commit();
        session.close();


        session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        for (int i = 0; i < idC.length; i++) {
            completed[i] = new BookRentContract(books.get(idC[i][0]),
                clients.get(idC[i][1]), LocalDate.now().minusDays(3),
                LocalDate.now().minusDays(1));
            completed[i + 5] = new BookRentContract(books.get(idC[i][0]),
                clients.get(idC[i][0]), LocalDate.of(2015 + i, 1, 1),
                LocalDate.of(2016 + i, 1, 1));

            session.persist(completed[i]);
            session.persist(completed[i + 5]);

            ongoing[i] = new BookRentContract(books.get(idO[i][0]),
                clients.get(idO[i][1]), LocalDate.of(2015 + i, i + 3, i + 4));
            session.persist(ongoing[i]);

        }
    }



    @Test
    @Order(1)
    void prepareData() {
        prepareAuthorData();
        prepareBookGenreData();
        prepareClientData();
        prepareBookData();
    }

    void prepareBookData() {
        Book[] books = new Book[5];

        for (int i = 0; i < books.length; i++) {
            books[i] = new Book(new BookInfo("Book " + i, 2000 + i));
            books[i].setBookStock(new BookStock(i, i + 0.5));
            session.persist(books[i]);
        }
    }

    void prepareClientData() {
        Client[] clients = new Client[5];

        for (int i = 0; i < clients.length; i++) {
            clients[i] = new Client(
                new ClientContactInfo("Client " + i, "Client address " + i),
                ClientTier.NORMAL);
            session.persist(clients[i]);
        }
    }

    void prepareBookGenreData() {
        BookGenre[] genres = new BookGenre[5];

        for (int i = 0; i < genres.length; i++) {
            genres[i] = new BookGenre("Book Genre " + i);
            session.persist(genres[i]);
        }
    }

    void prepareAuthorData() {
        Author[] authors = new Author[5];

        for (int i = 0; i < authors.length; i++) {
            authors[i] = new Author(new AuthorInfo("Author " + i, 20 + i));
            session.persist(authors[i]);
        }
    }



    @BeforeAll
    static void init() {
        ServiceRegistry registry =
            new StandardServiceRegistryBuilder().configure().build();

        Metadata metadata = null;
        try {
            metadata =
                new MetadataSources(registry)
                    .addAnnotatedClasses(Book.class, Author.class,
                        BookGenre.class, Client.class, BookRentContract.class)
                    .getMetadataBuilder()
                    .applyImplicitNamingStrategy(
                        ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
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
            session.getTransaction().commit();
        } catch (Exception e) {
            e.printStackTrace();
            session.getTransaction().rollback();
        } finally {
            session.close();
        }
    }


}
