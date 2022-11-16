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
        return getCurrentSession().createQuery(
            "from BookRentContract brc"
                + " where brc.startDate >= :from and brc.endDate <= :to and :now >= brc.endDate",
            BookRentContract.class)
            .setParameter("from", from)
            .setParameter("to", to)
            .setParameter("now", LocalDate.now())
            .getResultList();
    }

    @Override
    public List<BookRentContract> findByStatus(boolean isActive) {
        return getCurrentSession().createQuery(
            "from BookRentContract brc"
                + " where :now >= brc.endDate",
            BookRentContract.class)
            .setParameter("now", LocalDate.now())
            .getResultList();
    }

    @Override
    public void delete(BookRentContract entity) {
        getCurrentSession().remove(entity);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Can't delete all");
    }

    @Override
    public List<BookRentContract> fetch(int amount) {
        return getCurrentSession().createQuery(
            "from BookRentContract brc", BookRentContract.class)
            .setMaxResults(amount)
            .getResultList();
    }

    @Override
    public BookRentContract findById(Long id) {
        return getCurrentSession().find(BookRentContract.class, id);
    }

    @Override
    public void save(BookRentContract entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(BookRentContract entity) {
        getCurrentSession().merge(entity);
    }

}
