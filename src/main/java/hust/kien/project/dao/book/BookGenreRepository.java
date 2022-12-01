package hust.kien.project.dao.book;

import java.util.List;
import org.springframework.stereotype.Repository;
import hust.kien.project.dao.LibraryRepository;
import hust.kien.project.model.book.BookGenre;

@Repository
public interface BookGenreRepository extends LibraryRepository<BookGenre, String> {
    
    List<BookGenre> findByNameLikeIgnoreCase(String name);
}
