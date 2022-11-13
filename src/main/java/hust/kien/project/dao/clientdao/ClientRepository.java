package hust.kien.project.dao.clientdao;

import java.util.Objects;
import org.hibernate.SessionFactory;

public class ClientRepository extends AbstractClientRepository {
    private static ClientRepository singleton;

    /**
     * Should be a singleton, hence private
     */
    private ClientRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    /**
     * Factory method
     */
    public static ClientRepository getInstance(SessionFactory sessionFactory) {
        assert Objects.nonNull(sessionFactory) : "sessionFactory should not be null";
        if (singleton == null) {
            singleton = new ClientRepository(sessionFactory);
        }
        return singleton;
    }

}
