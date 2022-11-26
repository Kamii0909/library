package hust.kien.project.controller;

import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;
import hust.kien.project.model.rent.ActiveTicket;
import hust.kien.project.model.rent.ClosedTicket;
import hust.kien.project.service.LibraryMetadataService;
import hust.kien.project.service.TicketService;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;
import hust.kien.project.service.dynamic.ClientSpecficationBuilder;
import lombok.extern.slf4j.Slf4j;

@Component
@Profile("dev")
@Slf4j
public class CliController implements CommandLineRunner {

    @Autowired
    private LibraryMetadataService metadataService;

    @Autowired
    private TicketService ticketService;


    @EventListener(classes = ApplicationReadyEvent.class)
    public void run() {

        List<Author> authors = metadataService.findAuthorByNameContains("Aut");

        List<BookGenre> genres = metadataService.findGenreByNameContains("Gen");

        BookGenre genre = genres.get(random(genres.size()));
        Author author = authors.get(random(authors.size()));

        List<Book> books = metadataService
            .dynamicFind(new BookSpecificationBuilder()
                .initCollection()
                .authors()
                .genres()
                .back());
        Book book = books.get((int) (Math.random() * books.size()));

        book.getBookInfo().getAuthors().add(author);
        book.getBookInfo().getBookGenres().add(genre);

        book = metadataService.saveOrUpdate(book);

        Client client = metadataService
            .dynamicFind(new ClientSpecficationBuilder())
            .get(0);

        ActiveTicket activeTicket = ticketService.createActiveTicket(book, client);

        ActiveTicket activeTicket2 = ticketService.createActiveTicket(book, client);

        log.info("Created active ticket");

        System.out.println(activeTicket);
        System.out.println(activeTicket2);

        ClosedTicket closedTicket = ticketService.closeActiveTicket(activeTicket);

        System.out.println(closedTicket);
    }

    /**
     * Preload data
     */
    @Override
    public void run(String... args) throws Exception {
        int seed = 20;

        Book book = Book.builder()
            .name("Book " + random(seed))
            .releasedYear(1980 + random(40))
            .stock(random(seed) + 3)
            .reimburseCost(random(seed * 3))
            .build();
        book = metadataService.saveOrUpdate(book);

        BookGenre bookGenre = new BookGenre("Genre " + random(seed));
        metadataService.saveOrUpdate(bookGenre);

        Author author = Author.builder()
            .name("Author " + random(seed))
            .age(20 + random(40))
            .build();
        metadataService.saveOrUpdate(author);

        ClientTier[] tiers = ClientTier.values();

        Client client = Client.builder()
            .name("Client " + random(seed))
            .address("Address " + random(seed))
            .tier(tiers[random(tiers.length)]).build();
        metadataService.saveOrUpdate(client);

        
    }

    /**
     * Helper method
     */
    private int random(int max) {
        return (int) (Math.random() * max);
    }
}
