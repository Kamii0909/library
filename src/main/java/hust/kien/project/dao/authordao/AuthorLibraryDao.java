package hust.kien.project.dao.authordao;

import java.util.Collection;
import java.util.List;
import hust.kien.project.dao.LibraryDao;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo;
import hust.kien.project.model.book.Book;

public interface AuthorLibraryDao extends LibraryDao<Author, Long> {
    //-------------------------
    // FIND BY AUTHOR INFO
    //-------------------------

    //------ Author Name ------
    public List<Author> findByAuthorName(String name);

    //--------- Age -----------
    public List<Author> findByAgeExact(int age);

    public List<Author> findByAgeBetween(int from, int to);

    //-------- Book(s) ---------
    public List<Author> findWroteAtLeastOneOfBook(Collection<Book> books);

    public List<Author> findWroteAllOfBooks(Collection<Book> books);

    //---- Composite criteria ----
    /**
     * A clunky method for composite criteria query
     * <p>
     * {@code authorInfo} maybe null in some field, {@code books} is necessary managed by
     * persistence context
     * </p>
     */
    public List<Author> findFromAuthorInfo(AuthorInfo authorInfo);
    
    
}
