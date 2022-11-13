package hust.kien.project.dao.rentdao;

import java.util.Objects;
import org.hibernate.SessionFactory;

public class BookRentContractRepository extends AbstractBookRentContractRepository {
    private static BookRentContractRepository singleton;

    protected BookRentContractRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    public static BookRentContractRepository getInstance(SessionFactory sessionFactory){
        assert Objects.nonNull(sessionFactory) : "sessionFactory should not be null";
        if(singleton == null){
            singleton = new BookRentContractRepository(sessionFactory);
        }

        return singleton;
    }
    
}
