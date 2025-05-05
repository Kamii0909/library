package hust.kien.project.core.book;

import org.jspecify.annotations.NonNull;
import org.springframework.data.repository.NoRepositoryBean;

import hust.kien.project.core.dao.CrudRepository;
import hust.kien.project.core.model.book.Book;

@NoRepositoryBean
interface BookRepository extends CrudRepository<@NonNull Book, @NonNull Long> {
    
}
