package hust.kien.project.service.dynamic.ticket;

import hust.kien.project.model.ticket.Ticket;

public class TicketSpecificationBuilder extends AbstractTicketSpecificationBuilder<Ticket> {

    @Override
    public  Class<Ticket> libraryType() {
        return Ticket.class;
    }
    
}
