package hust.kien.project.controller;

import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.annotation.Profile;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;

@Component
@Profile("dev")
public class CLI {

    @Autowired
    private LibrarianService librarianService;

    @EventListener(classes = {ApplicationStartedEvent.class})
    void run() {
        ThreadLocalRandom random = ThreadLocalRandom.current();

        Scanner scanner = new Scanner(System.in);
        while (true) {
            String bookName = scanner.nextLine();

            if (bookName.isBlank()) {
                scanner.close();
                break;
            }

            Book book = Book.builder()
                .name(bookName)
                .releasedYear(random.nextInt(20) + 1990)
                .stock(random.nextInt(3) + 2)
                .reimburseCost(20.0)
                .build();


            System.out.println(librarianService.saveOrUpdate(book));
        }
    }
}
