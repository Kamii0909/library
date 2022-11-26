package hust.kien.project.dao;

import org.springframework.stereotype.Repository;
import hust.kien.project.model.rent.ClosedTicket;

@Repository
public interface ClosedTicketRepository extends LibraryRepository<ClosedTicket, Long> {
    
}
