package hust.kien.project.core.author;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;

@Repository
class AuthorRepository extends SimpleCrudRepository<@NonNull Author, @NonNull AuthorId> {

    public AuthorRepository(EntityManager entityManager) {
        super(Author.class, entityManager);
    }
}
