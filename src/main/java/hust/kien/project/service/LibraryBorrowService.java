package hust.kien.project.service;

import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.BorrowTicket;

public interface LibraryBorrowService {
    BorrowTicket createTicket(Book book, Client client);
}
