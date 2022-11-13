package hust.kien.project.controller;

import java.util.Scanner;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import hust.kien.project.dao.bookdao.BookRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.*;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.BookRentContract;
import hust.kien.project.service.BookService;

public class CommandLineController {

    public static void main(String[] args) {
        BookService bookService;
        SessionFactory sessionFactory = getSessionFactory();
        bookService = new BookService(sessionFactory);

        System.out.println("Library application");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1: add new book\n0: stop");

            int i = Integer.parseInt(scanner.nextLine());

            if (i == 0) {
                break;
            }

            if (i == 1) {
                saveBook(scanner, bookService);

                for (Book b : bookService.fetch(10)) {
                    System.out.println(b.getBookInfo().getBookName());
                }
            }
        }

        scanner.close();
    }

    private static void saveBook(Scanner scanner, BookService bookService) {
        BookInfo bookInfo = new BookInfo();

                System.out.println("The name of the book: ");
                bookInfo.setBookName(scanner.nextLine());

                System.out.println("Released year: ");
                bookInfo.setReleasedYear(Integer.parseInt(scanner.nextLine()));

                Book book = new Book(bookInfo);

                bookService.save(book);

    }

    private static SessionFactory getSessionFactory() {
        SessionFactory sessionFactory = null;
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(registry)
            .addAnnotatedClasses(Book.class, Author.class, BookGenre.class, Client.class,
                BookRentContract.class)
            .getMetadataBuilder()
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE).build();
        try {
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
            e.printStackTrace();
        }
        return sessionFactory;
    }
}
