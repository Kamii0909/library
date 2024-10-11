package hust.kien.project.model.client;

import hust.kien.project.model.ticket.LocalDateConverter;
import jakarta.persistence.Convert;
import jakarta.persistence.Embeddable;

import java.time.LocalDate;

@Embeddable
public class Subscription {
    @Convert(converter = LocalDateConverter.class)
    private LocalDate startDate;
    @Convert(converter = LocalDateConverter.class)
    private LocalDate endDate;

    public boolean isActive() {
        return LocalDate.now().compareTo(startDate) >= 0 && LocalDate.now().compareTo(endDate) <= 0;
    }


    public abstract static class SubscriptionBuilder<C extends Subscription, B extends SubscriptionBuilder<C, B>> {
        private LocalDate startDate;
        private LocalDate endDate;

        protected abstract B self();

        public abstract C build();

        public B startDate(final LocalDate startDate) {
            this.startDate = startDate;
            return self();
        }

        public B endDate(final LocalDate endDate) {
            this.endDate = endDate;
            return self();
        }
    }


    private static final class SubscriptionBuilderImpl
        extends SubscriptionBuilder<Subscription, SubscriptionBuilderImpl> {
        private SubscriptionBuilderImpl() {}

        @Override
        protected SubscriptionBuilderImpl self() {
            return this;
        }

        @Override
        public Subscription build() {
            return new Subscription(this);
        }
    }

    protected Subscription(final SubscriptionBuilder<?, ?> b) {
        this.startDate = b.startDate;
        this.endDate = b.endDate;
    }

    @SuppressWarnings("all")
    public static SubscriptionBuilder<?, ?> builder() {
        return new SubscriptionBuilderImpl();
    }

    public LocalDate getStartDate() {
        return this.startDate;
    }

    public LocalDate getEndDate() {
        return this.endDate;
    }

    public void setStartDate(final LocalDate startDate) {
        this.startDate = startDate;
    }

    public void setEndDate(final LocalDate endDate) {
        this.endDate = endDate;
    }

    public Subscription() {}
}
