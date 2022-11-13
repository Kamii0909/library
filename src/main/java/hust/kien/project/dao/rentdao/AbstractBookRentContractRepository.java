package hust.kien.project.dao.rentdao;

import java.time.LocalDate;
import java.util.List;
import org.hibernate.SessionFactory;
import hust.kien.project.dao.AbstractGeneralRepository;
import hust.kien.project.model.rent.BookRentContract;

public abstract class AbstractBookRentContractRepository extends AbstractGeneralRepository
    implements BookRentContractLibraryDao {

    protected AbstractBookRentContractRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<BookRentContract> findByDate(LocalDate from, LocalDate to) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<BookRentContract> findByStatus(boolean isActive) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void delete(BookRentContract entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<BookRentContract> fetch(int amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public BookRentContract findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(BookRentContract entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(BookRentContract entity) {
        // TODO Auto-generated method stub
        
    }

}
