package hust.kien.project.dao.authordao;

import java.util.Objects;
import org.hibernate.SessionFactory;

public class AuthorRepository extends AbstractAuthorRepository {
    private static AuthorRepository singleton;

    /**
     * Should be a singleton, hence private
     */
    private AuthorRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Factory method
     */
    public static AuthorRepository getInstance(SessionFactory sessionFactory) {
        assert Objects.nonNull(sessionFactory) : "sessionFactory should not be null";
        if (singleton == null) {
            singleton = new AuthorRepository(sessionFactory);
        }
        return singleton;
    }
}
