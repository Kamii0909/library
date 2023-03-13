package hust.kien.project.service.dynamic.ticket;

import hust.kien.project.model.ticket.ActiveTicket;

public class ActiveTicketSpecificationBuilder extends
    AbstractTicketSpecificationBuilder<ActiveTicket> {

    @Override
    public Class<ActiveTicket> libraryType() {
        return ActiveTicket.class;
    }
}
