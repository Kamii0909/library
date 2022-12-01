package hust.kien.project.service;

import hust.kien.project.model.book.Book;

public class OutOfStockException extends RuntimeException {

    private Book book;

    public OutOfStockException(Book book) {
        super("The book you attempted to borrow is currently out of stock\n" +
            "Details: \n" +
            "Book name: " + book.getBookInfo().getName() + "\n" +
            "Book id: " + book.getId() + "\n");

        this.book = book;
    }

    public Book getBook() {
        return book;
    }
}
