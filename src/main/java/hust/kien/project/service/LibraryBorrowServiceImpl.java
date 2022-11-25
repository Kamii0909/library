package hust.kien.project.service;

import hust.kien.project.model.book.Book;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.rent.BorrowTicket;
import hust.kien.project.model.rent.LocalDateConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;

@Service
@Transactional
public class LibraryBorrowServiceImpl implements LibraryBorrowService{

    @Autowired
    private LibraryMetadataService metadataService;

    @Override
    public BorrowTicket createTicket(Book book, Client client) {
        BorrowTicket newTicket = new BorrowTicket();
        newTicket.setBook(book);
        newTicket.setClient(client);

        newTicket.setStartDate(LocalDate.now());
        newTicket.setEndDate(LocalDateConverter.MAX);

        return metadataService.saveOrUpdate(newTicket);
    }
}
