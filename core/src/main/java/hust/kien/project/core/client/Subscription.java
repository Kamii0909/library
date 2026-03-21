package hust.kien.project.core.client;

import hust.kien.project.core.LocalDateAsStringType;
import jakarta.persistence.*;
import org.hibernate.annotations.JavaType;

import java.time.LocalDate;

import static hust.kien.project.core.Utils.idRequired;

@Entity
@Table(name = "Subscription")
class Subscription implements CurrentSubscription {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JoinColumn(name = "client_id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    private Client client;

    @JavaType(LocalDateAsStringType.class)
    private LocalDate start;

    @JavaType(LocalDateAsStringType.class)
    private LocalDate end;

    @Enumerated(EnumType.STRING)
    private Tier tier;

    protected Subscription() {
        // Hibernate
    }

    public Subscription(Client client, Tier tier, LocalDate start, LocalDate end) {
        this.client = client;
        this.tier = tier;
        this.start = start;
        this.end = end;
        if (end.isBefore(start)) {
            throw endIsBeforeStart();
        }
    }

    void extendTo(LocalDate newEnd) {
        if (newEnd.isBefore(this.start)) {
            throw endIsBeforeStart();
        }
        this.end = newEnd;
    }

    private static IllegalArgumentException endIsBeforeStart() {
        return new IllegalArgumentException("Invariance violated: start <= end");
    }

    public SubscriptionId id() {
        return SubscriptionId.of(idRequired(id));
    }

    public ClientId client() {
        return client.id();
    }

    @Override
    public LocalDate start() {
        return start;
    }

    @Override
    public LocalDate end() {
        return end;
    }

    @Override
    public Tier tier() {
        return tier;
    }
}
