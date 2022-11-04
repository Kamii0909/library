package hust.kien.project.dao;

import hust.kien.project.dao.authordao.AuthorLibraryDao;
import hust.kien.project.dao.bookdao.BookLibraryDao;
import hust.kien.project.model.Author;
import hust.kien.project.model.Book;
import hust.kien.project.model.BookGenre;
import jakarta.persistence.criteria.CriteriaBuilder;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public abstract class AbstractLibraryDao {
    private SessionFactory sessionFactory;
    private CriteriaBuilder cb;
    
    protected AbstractLibraryDao() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();
        Metadata metadata = new MetadataSources(registry)
            .addAnnotatedClasses(Book.class, Author.class, BookGenre.class)
            .getMetadataBuilder()
            .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
            .build();
        try {
            sessionFactory = metadata.getSessionFactoryBuilder().build();
            cb = sessionFactory.getCriteriaBuilder();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }
}
