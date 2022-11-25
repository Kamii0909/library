package hust.kien.project.dao;

import hust.kien.project.model.rent.Ticket;
import org.springframework.stereotype.Repository;

@Repository
public interface TicketRepository extends LibraryRepository<Ticket, Long> {

}
