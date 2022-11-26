package hust.kien.project.dao;

import org.springframework.stereotype.Repository;
import hust.kien.project.model.rent.ActiveTicket;

@Repository
public interface ActiveTicketRepository extends LibraryRepository<ActiveTicket, Long> {

}
