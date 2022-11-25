package hust.kien.project.dao;

import hust.kien.project.model.rent.BorrowTicket;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends LibraryRepository<BorrowTicket, Long> {

}
