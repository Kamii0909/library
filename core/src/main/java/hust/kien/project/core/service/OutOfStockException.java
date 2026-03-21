package hust.kien.project.core.service;

import hust.kien.project.core.book.ReadonlyBook;

public class OutOfStockException extends RuntimeException {

    private final transient ReadonlyBook book;

    public OutOfStockException(ReadonlyBook book) {
        super("The book you attempted to borrow is currently out of stock\n" +
                "Details: \n" +
                "Book name: " + book.name() + "\n" +
                "Book id: " + book.id() + "\n");

        this.book = book;
    }

    public ReadonlyBook getBook() {
        return book;
    }
}
