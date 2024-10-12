package hust.kien.project.core.dao.author;

import java.util.List;

import hust.kien.project.core.dao.LibraryRepository;
import hust.kien.project.core.model.author.Author;

public interface AuthorRepository
    extends LibraryRepository<Author, Long> {

    public List<Author> findByAuthorInfo_Name(String name);

    public List<Author> findByAuthorInfo_NameIgnoreCaseLike(String name);

    public List<Author> findByAuthorInfo_AgeBetween(int from, int to);

}
