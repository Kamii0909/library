package hust.kien.project.dao.authordao;

import java.util.Collection;
import java.util.List;
import org.hibernate.SessionFactory;
import hust.kien.project.dao.AbstractGeneralRepository;
import hust.kien.project.model.author.Author;
import hust.kien.project.model.author.AuthorInfo;
import hust.kien.project.model.author.AuthorInfo_;
import hust.kien.project.model.author.Author_;
import hust.kien.project.model.book.Book;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Root;

public abstract class AbstractAuthorRepository extends AbstractGeneralRepository implements AuthorLibraryDao {

    protected AbstractAuthorRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
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
    public void delete(Author entity) {
        getCurrentSession().remove(entity);
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
    public void save(Author entity) {
        getCurrentSession().persist(entity);
    }

    @Override
    public void update(Author entity) {
        getCurrentSession().merge(entity);
    }

}
