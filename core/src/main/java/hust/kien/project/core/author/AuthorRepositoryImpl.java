package hust.kien.project.core.author;

import org.jspecify.annotations.NonNull;
import org.springframework.stereotype.Repository;

import hust.kien.project.core.dao.SimpleCrudRepository;
import jakarta.persistence.EntityManager;

@Repository
class AuthorRepositoryImpl extends SimpleCrudRepository<@NonNull Author, @NonNull Long> implements AuthorRepository {
    public AuthorRepositoryImpl(EntityManager entityManager) {
        super(Author.class, entityManager);
    }
}
