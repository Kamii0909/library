package hust.kien.project.cli;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
// import org.springframework.stereotype.Component;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryRole;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;
import hust.kien.project.model.ticket.ActiveTicket;
import hust.kien.project.service.authorized.ManagerService;
import hust.kien.project.service.dynamic.AuthorSpecificationBuilder;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;
import hust.kien.project.service.dynamic.ClientSpecficationBuilder;
import hust.kien.project.service.internal.LibraryMetadataService;
import hust.kien.project.service.internal.TicketService;

// @Component
@Profile("dev")
@SuppressWarnings("unused")
public class CliController implements CommandLineRunner {

    @Autowired
    private LibraryMetadataService metadataService;

    @Autowired
    private TicketService ticketService;

    @Autowired
    private ManagerService managerService;

    private final Random r = new Random();

    /**
     * Preload data
     */
    @Override
    public void run(String... args) throws Exception {
        int seed = 20;

        // Add a book
        Book book;
        addBook(seed);

        // Add a book genre
        addBookGenre(seed);

        // Add an author
        addAuthor(seed);

        addClient(seed);

        book = addAuthorAndGenreToBook();

        book = metadataService.saveOrUpdate(book);

        // createAnActiveAndClosedTicketForBook(book);

        createLibraryEmployee(seed);

    }

    private void createLibraryEmployee(int seed) {
        // Create a new employee
        LibraryEmployee employee = addLibraryEmployee(seed);

        managerService.createUser(employee, employee.getUsername());
    }

    private void addAnActiveAndAClosedTicket(Book book) {
        Client client;
        List<Client> clients = metadataService.dynamicFind(new ClientSpecficationBuilder());

        // Get a random client
        client = clients.get(random(clients.size()));

        // Create a active ticket between them
        ActiveTicket activeTicket = ticketService.createActiveTicket(book, client);

        // Another one
        ticketService.createActiveTicket(book, client);

        // Close the first one
        ticketService.closeActiveTicket(activeTicket);
    }

    private LibraryEmployee addLibraryEmployee(int seed) {
        int i = random(seed) + 6;

        List<LibraryRole> roles = new ArrayList<>();
        for (int j = 0; j < 3; j++) {
            if (random(2) == 1) {
                roles.add(LibraryRole.values()[j]);
            }
        }

        return LibraryEmployee.builder()
            .username("kienht" + i)
            .employeeName("Ha Trung Kien " + i)
            .roles(roles)
            .build();
    }

    private Book addAuthorAndGenreToBook() {
        Book book;
        BookGenre genre;
        Author author;
        List<Author> authors =
            metadataService.dynamicFind(new AuthorSpecificationBuilder().nameContains("Aut"));

        List<BookGenre> genres = metadataService.findGenreByNameContains("Gen");

        genre = genres.get(random(genres.size()));
        author = authors.get(random(authors.size()));

        List<Book> books = metadataService
            .dynamicFind(new BookSpecificationBuilder()
                .initCollection()
                .authors()
                .genres()
                .back());

        // Get a random book
        book = books.get(random(books.size()));

        // Associate it with an extra author and a genre
        book.getBookInfo().getAuthors().add(author);
        book.getBookInfo().getBookGenres().add(genre);
        return book;
    }

    private void addClient(int seed) {
        ClientTier[] tiers = ClientTier.values();

        Client client = Client.builder()
            .name("Client " + random(seed))
            .address("Address " + random(seed))
            .tier(tiers[random(tiers.length)])
            .startDate(LocalDate.of(2022, 1 + random(seed / 3), 1))
            .endDate(LocalDate.of(2023, random(12), 1))
            .build();
        metadataService.saveOrUpdate(client);
    }

    private void addAuthor(int seed) {
        Author author = Author.builder()
            .name("Author " + random(seed))
            .age(20 + random(40))
            .build();
        metadataService.saveOrUpdate(author);
    }

    private void addBookGenre(int seed) {
        BookGenre genre = new BookGenre("Genre " + random(seed));
        metadataService.saveOrUpdate(genre);
    }

    private void addBook(int seed) {
        Book book = Book.builder()
            .name("Book " + random(seed))
            .releasedYear(1980 + random(40))
            .stock(random(seed) + 3)
            .reimburseCost(random(seed * 3))
            .build();
        metadataService.saveOrUpdate(book);
    }

    /**
     * Helper method
     */
    private int random(int max) {
        return r.nextInt(max);
    }
}
