package hust.kien.project.dao;

import hust.kien.project.dao.authordao.AuthorLibraryDao;
import hust.kien.project.dao.authordao.AuthorRepository;
import hust.kien.project.dao.bookdao.BookLibraryDao;
import hust.kien.project.dao.bookdao.BookRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.book.Book;
import hust.kien.project.model.book.BookGenre;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;

public abstract class AbstractRepository {
    private SessionFactory sessionFactory;
    private AuthorRepository authorRepository;
    private BookRepository bookRepository;
    
    protected AbstractRepository() {
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
            e.printStackTrace();
        }

        authorRepository = AuthorRepository.getInstance(sessionFactory);
        bookRepository = BookRepository.getInstance(sessionFactory);
    }
}
