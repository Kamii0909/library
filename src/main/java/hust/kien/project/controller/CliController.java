package hust.kien.project.controller;

import java.util.Scanner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookInfo;
import hust.kien.project.model.book.BookStock;
import hust.kien.project.service.LibraryMetadataService;
import hust.kien.project.service.dynamic.BookSpecificationBuilder;

@Component
public class CliController {

    @Autowired
    private LibraryMetadataService metadataService;

    @EventListener(classes = ApplicationReadyEvent.class)
    public void run() {

        Scanner scanner = new Scanner(System.in);

        loop: while (true) {
            System.out.println("0: stop");
            System.out.println("1: add");
            System.out.println("2: find");
            System.out.println("3: update");

            switch (Integer.parseInt(scanner.nextLine())) {
                case 0:
                    break loop;

                case 1:
                    System.out.println("Choose which one to add:");
                    System.out.println("1: book");
                    System.out.println("2: author");
                    System.out.println("3: client");

                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            System.out.println("Adding book");

                            BookInfo bookInfo = new BookInfo();

                            System.out.println("Book name:");
                            bookInfo.setName(scanner.nextLine());

                            System.out.println("Released year:");
                            bookInfo.setReleasedYear(Integer.parseInt(scanner.nextLine()));

                            BookStock bookStock = new BookStock();

                            System.out.println("Current stock:");
                            bookStock.setStock(Integer.parseInt(scanner.nextLine()));

                            System.out.println("Reimburse cost:");
                            bookStock.setReimburseCost(Double.parseDouble(scanner.nextLine()));

                            metadataService.saveOrUpdateBook(new Book(bookInfo, bookStock));

                            break;
                        default:
                            break;
                    }

                    break;
                case 2:
                    System.out.println("Choose which one to find:");
                    System.out.println("1: book");
                    System.out.println("2: author");
                    System.out.println("3: client");
                    switch (Integer.parseInt(scanner.nextLine())) {
                        case 1:
                            BookSpecificationBuilder builder = new BookSpecificationBuilder();

                            System.out.println("Name:");
                            builder.nameContains(scanner.nextLine());

                            System.out.println("From:");
                            int from = Integer.parseInt(scanner.nextLine());
                            System.out.println("To:");
                            builder.releasedBetween(from, Integer.parseInt(scanner.nextLine()));

                            System.out.println(metadataService.dynamicFindBook(builder));

                            break;

                        default:
                            break;
                    }
                    break;
                default:
                    break;

                case 3:
                    Book book = metadataService
                        .dynamicFindBook(new BookSpecificationBuilder()
                            .nameContains("Boo").setInitCollections(true))
                        .get(0);

                    Author author = metadataService.findAuthorByNameContains("1").get(0);

                    book.getBookInfo().getAuthors().add(author);

                    metadataService.saveOrUpdateBook(book);

                    break;

            }
        }

        scanner.close();

    }
}
