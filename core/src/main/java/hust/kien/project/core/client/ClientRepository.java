package hust.kien.project.core.client;

import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;
import org.springframework.stereotype.Repository;

@Repository
class ClientRepository extends SimpleCrudRepository<Client, Long> {
    protected ClientRepository(EntityManager entityManager) {
        super(Client.class, entityManager);
    }
}
