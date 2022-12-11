package hust.kien.project.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationStartedEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import hust.kien.project.model.book.Book;
import hust.kien.project.service.authorized.LibrarianService;

@Component
public class CLI {

    @Autowired
    private LibrarianService librarianService;

    @EventListener(classes = {ApplicationStartedEvent.class})
    void run() {
        Book book = Book.builder()
            .name("Sach cua toi")
            .releasedYear(2022)
            .stock(3)
            .reimburseCost(4.0)
            .build();

        librarianService.saveOrUpdate(book);
    }
}
