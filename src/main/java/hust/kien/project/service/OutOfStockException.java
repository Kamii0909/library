package hust.kien.project.service;

public class OutOfStockException extends RuntimeException {

    public OutOfStockException(String message) {
        super("The book you are trying to borrow is out of stock: " + message);
    }
}
