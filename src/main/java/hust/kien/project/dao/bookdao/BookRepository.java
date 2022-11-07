package hust.kien.project.dao.bookdao;

import org.hibernate.SessionFactory;

import java.util.Objects;

public class BookRepository extends AbstractBookLibraryDao {
    private static BookRepository singleton;

    /**
     * Should be a singleton, hence private
     */
    private BookRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Factory method
     */
    public static BookRepository getInstance(SessionFactory sessionFactory){
        assert Objects.nonNull(sessionFactory) : "sessionFactory should not be null";
        if(singleton == null){
            singleton = new BookRepository(sessionFactory);
        }
        return singleton;
    }

}
