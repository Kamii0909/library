package hust.kien.project.core.model.ticket;

import java.time.LocalDate;

import org.hibernate.annotations.JavaType;

import hust.kien.project.core.LocalDateAsStringType;
import jakarta.persistence.Entity;

@Entity
public class ActiveTicket extends Ticket {
    @JavaType(LocalDateAsStringType.class)
    private LocalDate endDate;

    private static LocalDate $default$endDate() {
        return LocalDateConverter.MAX;
    }

    @SuppressWarnings("all")
    public abstract static class ActiveTicketBuilder<C extends ActiveTicket, B extends ActiveTicketBuilder<C, B>>
        extends TicketBuilder<C, B> {
        private boolean endDate$set;
        private LocalDate endDate$value;

        @Override
        protected abstract B self();

        @Override
        public abstract C build();

        public B endDate(final LocalDate endDate) {
            this.endDate$value = endDate;
            endDate$set = true;
            return self();
        }

        @Override
        public String toString() {
            return "ActiveTicket.ActiveTicketBuilder(super=" + super.toString() + ", endDate$value="
                + this.endDate$value + ")";
        }
    }


    private static final class ActiveTicketBuilderImpl
        extends ActiveTicketBuilder<ActiveTicket, ActiveTicketBuilderImpl> {
        private ActiveTicketBuilderImpl() {}

        @Override
        protected ActiveTicketBuilderImpl self() {
            return this;
        }

        @Override
        public ActiveTicket build() {
            return new ActiveTicket(this);
        }
    }

    protected ActiveTicket(final ActiveTicketBuilder<?, ?> b) {
        super(b);
        if (b.endDate$set)
            this.endDate = b.endDate$value;
        else
            this.endDate = ActiveTicket.$default$endDate();
    }

    @SuppressWarnings("all")
    public static ActiveTicketBuilder<?, ?> builder() {
        return new ActiveTicketBuilderImpl();
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    @Override
    public String toString() {
        return "ActiveTicket(super=" + super.toString() + ", endDate=" + this.getEndDate() + ")";
    }

    public ActiveTicket() {}
}
