package hust.kien.project.controller;

import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.BookRentContract;
import hust.kien.project.service.BookServiceImpl;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.Scanner;

public class CommandLineController {

    public static void main(String[] args) {
        BookServiceImpl bookService;
        SessionFactory sessionFactory = getSessionFactory();
        bookService = new BookServiceImpl(sessionFactory);

        System.out.println("Library application");

        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("1: add new book\n0: stop");

            int i = Integer.parseInt(scanner.nextLine());

            if (i == 0) {
                break;
            }

            else if (i == 1) {
                saveBook(scanner, bookService);
            }

            else if(i == 2) {
                for (Book b : bookService.fetch(10)) {
                    System.out.println(b.getBookInfo().getBookName());
                    System.out.println(b.getBookInfo().getAuthors());
                }
            }
        }

        scanner.close();
    }

    private static void saveBook(Scanner scanner, BookServiceImpl bookService) {
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
