package hust.kien.project.dao.bookdao;

import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import hust.kien.project.model.book.BookInfo;
import hust.kien.project.model.book.BookInfo_;
import hust.kien.project.model.book.BookStock;
import hust.kien.project.model.book.BookStock_;
import hust.kien.project.model.book.Book_;
import hust.kien.project.model.rent.BookRentContract;
import hust.kien.project.model.rent.BookRentContract_;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Join;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import jakarta.persistence.criteria.SetJoin;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import java.time.LocalDate;
import java.util.Collection;
import java.util.List;
import java.util.Optional;

public abstract class AbstractBookLibraryDao implements BookLibraryDao {
    private final SessionFactory sessionFactory;
    private final CriteriaBuilder cb;

    /**
     * Should be a singleton
     */
    protected AbstractBookLibraryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        cb = sessionFactory.getCriteriaBuilder();
    }

    @Override
    public List<Book> findByBookName(String name) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookInfo.bookName like :bookName", Book.class)
            .setParameter("bookName", "%" + name + "%").getResultList();
    }

    @Override
    public Optional<Book> findAnyByBookName(String name) {
        return findByBookName(name).stream().findAny();
    }

    @Override
    public List<Book> findByReleasedYear(int year) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookInfo.releasedYear = :year", Book.class)
            .setParameter("year", year).getResultList();
    }

    @Override
    public Optional<Book> findAnyByReleasedYear(int year) {
        return findAnyByReleasedYear(year).stream().findAny();
    }

    @Override
    public List<Book> findByReleasedYearBetween(int from, int to) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        cq.where(cb.between(root.get(Book_.bookInfo).get(BookInfo_.releasedYear), from, to));
        return getCurrentSession().createQuery(cq).getResultList();
    }

    @Override
    public Optional<Book> findAnyByReleasedYearBetween(int from, int to) {
        return findByReleasedYearBetween(from, to).stream().findAny();
    }

    @Override
    public List<Book> findByAuthor(Author author) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.authors a where a.id = :id ", Book.class)
            .setParameter("id", author.getId()).getResultList();
    }

    @Override
    public Optional<Book> findAnyByAuthor(Author author) {
        return findByAuthor(author).stream().findAny();
    }

    @Override
    public List<Book> findWrittenByAtLeastOne(Collection<Author> authors) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.authors a where a in :authors", Book.class)
            .setParameterList("authors", authors).getResultList();
    }

    @Override
    public Optional<Book> findAnyWrittenByAtLeastOne(Collection<Author> authors) {
        return findWrittenByAtLeastOne(authors).stream().findAny();
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
    public Optional<Book> findAnyWrittenByAll(Collection<Author> authors) {
        return findWrittenByAll(authors).stream().findAny();
    }

    @Override
    public List<Book> findFromGenre(BookGenre genre) {
        return getCurrentSession()
            .createQuery("from Book b join b.bookInfo.bookGenres g where g.name = :name",
                Book.class)
            .setParameter("name", genre.getName()).getResultList();
    }

    @Override
    public Optional<Book> findAnyFromGenre(BookGenre genre) {
        return findFromGenre(genre).stream().findAny();
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
    public Optional<Book> findAnyMatchAtLeastOneGenre(Collection<BookGenre> genres) {
        return findMatchAtLeastOneGenre(genres).stream().findAny();
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

    @Override
    public Optional<Book> findAnyMatchAllGenres(Collection<BookGenre> genres) {
        return findMatchAllGenres(genres).stream().findAny();
    }

    /**
     * TODO
     */
    @Override
    public List<Book> findFromBookInfo(BookInfo bookInfoInfo) {
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public Transaction beginTransaction() {
        return getCurrentSession().beginTransaction();
    }

    @Override
    public void commitTransaction() {
        Transaction tx = getCurrentSession().getTransaction();
        try {
            tx.commit();
        } catch (Exception e) {
            tx.rollback();
            e.printStackTrace();
        }
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
        if (getCurrentSession().contains(entity))
            getCurrentSession().remove(entity);
        else
            throw new PersistentObjectException("Object is not a managed entity");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Can't delete all");
    }

    @Override
    public void update(Book entity) {
        if (getCurrentSession().contains(entity))
            getCurrentSession().merge(entity);
        else
            throw new PersistentObjectException("Use save to save new Book");
    }

    @Override
    public Optional<Book> findAnyByAllContractDateFrom(LocalDate from, LocalDate to) {
        return findByAllContractDateFrom(from, to).stream().findAny();
    }

    @Override
    public Optional<Book> findAnyByAtLeastOneContractDateFrom(LocalDate from, LocalDate to) {
        return findByAtLeastOneContractDateFrom(from, to).stream().findAny();
    }

    @Override
    public Optional<Book> findAnyByReimburseCostBetween(double from, double to) {
        return findByReimburseCostBetween(from, to).stream().findAny();
    }

    @Override
    public Optional<Book> findAnyByStockBetween(int from, int to) {
        return findByStockBetween(from, to).stream().findAny();
    }


    @Override
    public List<Book> findByAllContractDateFrom(LocalDate from, LocalDate to) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        Join<Book, BookStock> joinBookStock = root.join(Book_.bookStock);

        Join<BookStock, BookRentContract> joinOngoing =
            joinBookStock.join(BookStock_.ongoingContracts);

        Join<BookStock, BookRentContract> joinCompleted =
            joinBookStock.join(BookStock_.completedContracts);

        Predicate predOngoingGreaterThan =
            cb.greaterThan(joinOngoing.get(BookRentContract_.startDate), from);

        Predicate predCompletedLessThan =
            cb.lessThan(joinCompleted.get(BookRentContract_.endDate), to);
        Predicate predCompletedGreaterThan =
            cb.greaterThan(joinCompleted.get(BookRentContract_.startDate), from);

        return getCurrentSession()
            .createQuery(
                cq.where(predOngoingGreaterThan, predCompletedLessThan, predCompletedGreaterThan))
            .getResultList();
    }

    @Override
    public List<Book> findByAtLeastOneContractDateFrom(LocalDate from, LocalDate to) {
        CriteriaQuery<Book> cq = cb.createQuery(Book.class);
        Root<Book> root = cq.from(Book.class);

        Join<Book, BookStock> joinBookStock = root.join(Book_.bookStock);

        Join<BookStock, BookRentContract> joinOngoing =
            joinBookStock.join(BookStock_.ongoingContracts);

        Join<BookStock, BookRentContract> joinCompleted =
            joinBookStock.join(BookStock_.completedContracts);
        
        

        return null;
    }

    @Override
    public List<Book> findByReimburseCostBetween(double from, double to) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Book> findByStockBetween(int from, int to) {
        // TODO Auto-generated method stub
        return null;
    }
}
