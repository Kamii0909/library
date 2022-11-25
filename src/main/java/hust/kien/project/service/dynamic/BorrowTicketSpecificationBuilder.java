package hust.kien.project.service.dynamic;

import hust.kien.project.model.rent.BorrowTicket;

public class BorrowTicketSpecificationBuilder extends GeneralLibrarySpecificationBuilder<BorrowTicket> {

    @Override
    public Class<BorrowTicket> libraryType() {
        return BorrowTicket.class;
    }

    @Override
    public BorrowTicketCollectionInitBuilder initCollection() {
        return new BorrowTicketCollectionInitBuilder();
    }

    public class BorrowTicketCollectionInitBuilder extends LibraryCollectionInitBuilder<BorrowTicket> {
        @Override
        public BorrowTicketSpecificationBuilder back() {
            return BorrowTicketSpecificationBuilder.this;
        }
    }
}
