package hust.kien.project.dao.bookdao;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;
import hust.kien.project.dao.AbstractGeneralRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;
import hust.kien.project.model.book.BookInfo_;
import hust.kien.project.model.book.Book_;
import hust.kien.project.model.rent.BookRentContract;
import hust.kien.project.model.rent.BookRentContract_;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.Subquery;

public abstract class AbstractBookRepository extends AbstractGeneralRepository
    implements BookLibraryDao {
        
    /**
     * Should be a singleton
     */
    protected AbstractBookRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public List<Book> findByBookName(String name) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookInfo.bookName like :bookName", Book.class)
            .setParameter("bookName", "%" + name + "%").getResultList();
    }

    @Override
    public List<Book> findByReleasedYear(int year) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookInfo.releasedYear = :year", Book.class)
            .setParameter("year", year).getResultList();
    }

    @Override
    public List<Book> findByReleasedYearBetween(int from, int to) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        cq.where(cb.between(root.get(Book_.bookInfo).get(BookInfo_.releasedYear), from, to));
        return getCurrentSession().createQuery(cq).getResultList();
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.authors a where a.id = :id ", Book.class)
            .setParameter("id", author.getId()).getResultList();
    }

    @Override
    public List<Book> findWrittenByAtLeastOne(Collection<Author> authors) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.authors a where a in :authors", Book.class)
            .setParameterList("authors", authors).getResultList();
    }

    @Override
    public List<Book> findWrittenByAll(Collection<Author> authors) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        Author[] authorsArray = authors.toArray(Author[]::new);

        Predicate[] predicates = new Predicate[authorsArray.length];

        for (int i = 0; i < authorsArray.length; i++) {
            predicates[i] =
                cb.isMember(authorsArray[i], root.get(Book_.bookInfo).get(BookInfo_.authors));
        }
        return getCurrentSession().createQuery(cq.where(predicates)).getResultList();
    }

    @Override
    public List<Book> findFromGenre(BookGenre genre) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.bookGenres g where g.name = :name",
                Book.class)
            .setParameter("name", genre.getName()).getResultList();
    }

    @Override
    public List<Book> findMatchAtLeastOneGenre(Collection<BookGenre> genres) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.bookGenres g where g.name in :names",
                Book.class)
            .setParameterList("names", genres.stream().map(BookGenre::getName).toList())
            .getResultList();
    }

    @Override
    public List<Book> findMatchAllGenres(Collection<BookGenre> genres) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        BookGenre[] genresArray = genres.toArray(BookGenre[]::new);

        Predicate[] predicates = new Predicate[genresArray.length];

        for (int i = 0; i < genresArray.length; i++) {
            predicates[i] =
                cb.isMember(genresArray[i], root.get(Book_.bookInfo).get(BookInfo_.bookGenres));
        }

        return getCurrentSession().createQuery(cq.where(predicates)).getResultList();
    }

    /**
     * TODO
     */
    @Override
    public List<Book> findFromBookInfo(BookInfo bookInfoInfo) {
        throw new UnsupportedOperationException();
    }


    @Override
    public Book findById(Long aLong) {
        return getCurrentSession().find(Book.class, aLong);
    }

    @Override
    public List<Book> fetch(int amount) {
        return getCurrentSession().createQuery("from Book", Book.class).setMaxResults(amount)
            .list();
    }

    @Override
    public void save(Book entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void delete(Book entity) {
        getCurrentSession().remove(entity);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Can't delete all");
    }

    @Override
    public void update(Book entity) {
        getCurrentSession().merge(entity);
    }


    @Override
    public List<Book> findByAllContractDateFrom(LocalDate from, LocalDate to) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        Subquery<Book> sq = cq.subquery(Book.class);
        Root<BookRentContract> subRoot = sq.from(BookRentContract.class);

        sq.select(subRoot.get(BookRentContract_.book)).where(
            cb.equal(root, subRoot.get(BookRentContract_.book)),
            cb.lessThan(subRoot.get(BookRentContract_.endDate), LocalDate.now()),
            cb.or(
                cb.greaterThanOrEqualTo(subRoot.get(BookRentContract_.endDate),
                    LocalDate.ofYearDay(2023, 2)),
                cb.lessThanOrEqualTo(subRoot.get(BookRentContract_.startDate),
                    LocalDate.ofYearDay(2014, 2))));

        cq.where(cb.not(cb.exists(sq)));


        return getCurrentSession().createQuery(cq).getResultList();
    }

    @Override
    public List<Book> findByAtLeastOneContractDateFrom(LocalDate from, LocalDate to) {
        return getCurrentSession().createQuery(
            "from Book b join BookRentContract brc on b.id = brc.book where brc.startDate >= :from and brc.endDate <= :to",
            Book.class).setParameter("from", from).setParameter("to", to).getResultList();
    }

    @Override
    public List<Book> findByReimburseCostBetween(double from, double to) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookStock.reimburseCost between :from and :to",
                Book.class)
            .setParameter("from", from).setParameter("to", to).getResultList();
    }

    @Override
    public List<Book> findByStockBetween(int from, int to) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookStock.stock between :from and :to", Book.class)
            .setParameter("from", from).setParameter("to", to).getResultList();
    }
}
