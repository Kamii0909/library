package hust.kien.project.core.model.ticket;

import jakarta.persistence.Convert;
import jakarta.persistence.Entity;

import java.time.LocalDate;

@Entity
public class ClosedTicket extends Ticket {
    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;


    public abstract static class ClosedTicketBuilder<C extends ClosedTicket, B extends ClosedTicketBuilder<C, B>>
        extends TicketBuilder<C, B> {
        private LocalDate endDate;

        @Override
        protected abstract B self();

        @Override
        public abstract C build();

        public B endDate(final LocalDate endDate) {
            this.endDate = endDate;
            return self();
        }
    }


    private static final class ClosedTicketBuilderImpl
        extends ClosedTicketBuilder<ClosedTicket, ClosedTicketBuilderImpl> {
        private ClosedTicketBuilderImpl() {}

        @Override
        protected ClosedTicketBuilderImpl self() {
            return this;
        }

        @Override
        public ClosedTicket build() {
            return new ClosedTicket(this);
        }
    }

    protected ClosedTicket(final ClosedTicketBuilder<?, ?> b) {
        super(b);
        this.endDate = b.endDate;
    }

    @SuppressWarnings("all")
    public static ClosedTicketBuilder<?, ?> builder() {
        return new ClosedTicketBuilderImpl();
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ClosedTicket(super=" + super.toString() + ", endDate=" + this.getEndDate() + ")";
    }

    public ClosedTicket() {}
}
