package hust.kien.project.dao;

import java.io.Serializable;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;

public abstract class AbstractGeneralRepository<T,Id extends Serializable> implements LibraryDao<T,Id> {
    private final SessionFactory sessionFactory;
    protected final CriteriaBuilder cb;
    private final Logger logger;

    protected AbstractGeneralRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        this.cb = sessionFactory.getCriteriaBuilder();
        this.logger = LoggerFactory.getLogger(AbstractGeneralRepository.class);
    }

    @Override
    public Transaction beginTransaction() {
        return getCurrentSession().beginTransaction();
    }

    @Override
    public void commitTransaction() {
        try {
            getCurrentSession().getTransaction().commit();
        } catch (Exception e) {
            getCurrentSession().getTransaction().rollback();
            logger.warn("Exception occured at thread " + Thread.currentThread(), e);
        } finally {
            getCurrentSession().close();
        }
    }

    @Override
    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }
    
}
