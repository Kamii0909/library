package hust.kien.project.core.author;

import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.NoRepositoryBean;

import hust.kien.project.core.dao.CrudRepository;

@NoRepositoryBean
interface AuthorRepository extends CrudRepository<@NonNull Author, @NonNull Long> {
    
}
