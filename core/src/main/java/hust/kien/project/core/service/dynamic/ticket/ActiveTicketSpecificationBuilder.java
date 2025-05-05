package hust.kien.project.core.service.dynamic.ticket;

import hust.kien.project.core.model.ticket.ActiveTicket;

public class ActiveTicketSpecificationBuilder extends
    AbstractTicketSpecificationBuilder<ActiveTicket> {

    @Override
    public Class<ActiveTicket> libraryType() {
        return ActiveTicket.class;
    }
}
