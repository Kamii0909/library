package hust.kien.project.core.service;

import hust.kien.project.core.model.book.Book;

public class OutOfStockException extends RuntimeException {

    private final transient Book book;

    public OutOfStockException(Book book) {
        super("The book you attempted to borrow is currently out of stock\n" +
            "Details: \n" +
            "Book name: " + book.getBookInfo().name() + "\n" +
            "Book id: " + book.getId() + "\n");

        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
