package hust.kien.project.dao;

import java.util.List;
import org.springframework.stereotype.Repository;
import hust.kien.project.model.author.Author;

@Repository
public interface AuthorRepository extends LibraryRepository<Author, Long> {

    public List<Author> findByAuthorInfo_Name(String name);

    public List<Author> findByAuthorInfo_NameIgnoreCaseLike(String name);

    public List<Author> findByAuthorInfo_AgeBetween(int from, int to);

}
