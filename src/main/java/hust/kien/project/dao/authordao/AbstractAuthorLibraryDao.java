package hust.kien.project.dao.authordao;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo;
import hust.kien.project.model.author.AuthorInfo_;
import hust.kien.project.model.author.Author_;
import hust.kien.project.model.book.Book;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class AbstractAuthorLibraryDao implements AuthorLibraryDao {
    private final SessionFactory sessionFactory;
    private final CriteriaBuilder cb;

    protected AbstractAuthorLibraryDao(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
        cb = sessionFactory.getCriteriaBuilder();
    }

    @Override
    public Optional<Author> findAnyByAgeBetween(int from, int to) {
        return findByAgeBetween(from, to).stream().findAny();
    }

    @Override
    public Optional<Author> findAnyByAgeExact(int age) {
        return findByAgeExact(age).stream().findAny();
    }

    @Override
    public Optional<Author> findAnyByAuthorName(String name) {
        return findByAuthorName(name).stream().findAny();
    }

    @Override
    public Optional<Author> findAnyWroteAllOfBooks(Collection<Book> books) {
        return findWroteAllOfBooks(books).stream().findAny();
    }


    @Override
    public Optional<Author> findAnyWroteAtLeastOneOfBook(Collection<Book> books) {
        return findWroteAtLeastOneOfBook(books).stream().findAny();
    }


    @Override
    public List<Author> findByAgeBetween(int from, int to) {
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        cq.where(cb.between(root.get(Author_.authorInfo).get(AuthorInfo_.age), from, to));

        return getCurrentSession().createQuery(cq).getResultList();
    }

    @Override
    public List<Author> findByAgeExact(int age) {
        return getCurrentSession().createQuery("from Author a where a.age = :age", Author.class)
            .setParameter("age", age).getResultList();
    }

    @Override
    public List<Author> findByAuthorName(String name) {
        return getCurrentSession()
            .createQuery("from Author a where a.name like :name", Author.class)
            .setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public List<Author> findFromAuthorInfo(AuthorInfo authorInfo) {
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        if (!authorInfo.getName().isBlank())
            cq.where(cb.like(root.get(Author_.authorInfo).get(AuthorInfo_.name),
                "%" + authorInfo.getName() + "%"));
                
        // TODO
        throw new UnsupportedOperationException();
    }

    @Override
    public List<Author> findWroteAllOfBooks(Collection<Book> books) {
        CriteriaQuery<Author> cq = cb.createQuery(Author.class);
        Root<Author> root = cq.from(Author.class);

        for (Book book : books)
            cq.where(cb.isMember(book, root.get(Author_.authorInfo).get(AuthorInfo_.books)));

        return getCurrentSession().createQuery(cq).getResultList();
    }

    @Override
    public List<Author> findWroteAtLeastOneOfBook(Collection<Book> books) {
        return getCurrentSession()
            .createQuery("from Author a join a.authorInfo.books b where b in :books", Author.class)
            .setParameter("books", books).getResultList();
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
    public void delete(Author entity) {
        if (getCurrentSession().contains(entity)) {
            getCurrentSession().remove(entity);
        } else
            throw new PersistentObjectException("Author " + entity + " is not a managed entity");

    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Can't delete all authors");

    }

    @Override
    public List<Author> fetch(int amount) {
        return getCurrentSession().createQuery("from Author", Author.class).setMaxResults(amount)
            .getResultList();
    }

    @Override
    public Author findById(Long id) {
        return getCurrentSession().find(Author.class, id);
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    @Override
    public void save(Author entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(Author entity) {
        if (getCurrentSession().contains(entity))
            getCurrentSession().merge(entity);
        else
            throw new PersistentObjectException("Use save to save new Author");

    }

}
