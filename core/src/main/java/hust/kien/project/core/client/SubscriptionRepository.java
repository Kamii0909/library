package hust.kien.project.core.client;

import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;
import org.hibernate.Session;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
class SubscriptionRepository extends SimpleCrudRepository<Subscription, Long> {
    public SubscriptionRepository(EntityManager entityManager) {
        super(Subscription.class, entityManager);
    }

    @Nullable
    Subscription findClientSubscriptionAt(Long clientId, LocalDate time) {
        return entityManager.unwrap(Session.class)
                .createQuery("""
                        select S
                        from Subscription S
                        where S.client.id = :clientId
                          and :time between S.start and S.end
                        """, Subscription.class)
                .getSingleResultOrNull();
    }


}
