package hust.kien.project.dao;

import hust.kien.project.model.Author;
import hust.kien.project.model.Book;
import hust.kien.project.model.BookGenre;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.PersistentObjectException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

import java.util.List;

public abstract class AbstractBookLibraryDao implements LibraryDao<Book, Long> {
    private SessionFactory sessionFactory;

    public AbstractBookLibraryDao() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(registry)
            .addAnnotatedClasses(Book.class, Author.class, BookGenre.class)
            .getMetadataBuilder()
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
            .build();
        try {
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @Override
    public Book findById(Long aLong) {
        return getCurrentSession().find(Book.class, aLong);
    }

    @Override
    public List<Book> fetch(int amount) {
        return getCurrentSession().createQuery("from Book", Book.class).setMaxResults(amount).list();
    }

    @Override
    public List<Book> findByName(String name) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookInfo.bookName like :bookName", Book.class)
            .setParameter("bookName", "%" + name + "%")
            .list();
    }

    @Override
    public void save(Book entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void delete(Book entity) {
        if (getCurrentSession().contains(entity)) getCurrentSession().remove(entity);
        else throw new PersistentObjectException("Object is not a managed persistence");
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Can't delete all");
    }

    @Override
    public void update(Book entity) {
        if(getCurrentSession().contains(entity))
            getCurrentSession().merge(entity);
        else throw new PersistentObjectException("Use save to save new Book");
    }

    public List<Book> findByReleasedYear(int year) {
        return getCurrentSession()
            .createQuery("from Book b where b.bookInfo.releasedYear = :year", Book.class)
            .setParameter("year", year)
            .list();
    }

    protected Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    public Transaction beginTransaction() {
        return getCurrentSession().beginTransaction();
    }

    public void commitTransaction() {
        Session session = getCurrentSession();
        session.getTransaction().commit();
        session.close();
    }


}
