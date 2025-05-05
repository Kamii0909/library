package hust.kien.project.core.service.dynamic.ticket;

import hust.kien.project.core.model.ticket.Ticket;

public class TicketSpecificationBuilder extends AbstractTicketSpecificationBuilder<Ticket> {

    @Override
    public  Class<Ticket> libraryType() {
        return Ticket.class;
    }
    
}
