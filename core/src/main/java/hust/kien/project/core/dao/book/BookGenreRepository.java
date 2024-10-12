package hust.kien.project.core.dao.book;

import java.util.List;

import hust.kien.project.core.dao.LibraryRepository;
import hust.kien.project.core.model.book.BookGenre;

public interface BookGenreRepository extends LibraryRepository<BookGenre, String> {
    
    List<BookGenre> findByNameLikeIgnoreCase(String name);
}
