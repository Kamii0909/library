package hust.kien.project.core.ticket;

import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.hibernate.type.StandardBasicTypes;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
class TicketRepository extends SimpleCrudRepository<Ticket, Long> {
    protected TicketRepository(EntityManager entityManager) {
        super(Ticket.class, entityManager);
    }

    List<Ticket> activeTicketsOf(Long bookId) {
        return entityManager.unwrap(Session.class)
                .createQuery("""
                        select T from Ticket T where :now between T.start and T.end
                        and T.bookId = :bookId
                        """, Ticket.class)
                .setParameter("now", LocalDate.now(), StandardBasicTypes.LOCAL_DATE)
                .setParameter("bookId", bookId, StandardBasicTypes.LONG)
                .list();
    }
}
