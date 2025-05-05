package hust.kien.project.core.dao.author;

import java.util.List;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.dao.LibraryRepository;

public interface AuthorRepository
    extends LibraryRepository<Author, Long> {

    public List<Author> findByName(String name);

    public List<Author> findByNameIgnoreCaseLike(String name);

    public List<Author> findByAgeBetween(int from, int to);

}
