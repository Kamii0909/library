package hust.kien.project.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import jakarta.persistence.criteria.CriteriaBuilder;

/**
 * Implementing transactional methods 
 */
public abstract class AbstractGeneralRepository {
    private final SessionFactory sessionFactory;
    protected final CriteriaBuilder cb;
    private final Logger logger;

    protected AbstractGeneralRepository(SessionFactory sessionFactory){
        this.sessionFactory = sessionFactory;
        this.cb = sessionFactory.getCriteriaBuilder();
        this.logger = LoggerFactory.getLogger(AbstractGeneralRepository.class);
    }

    public Transaction beginTransaction() {
        return getCurrentSession().beginTransaction();
    }

    public void commitTransaction() {
        try {
            getCurrentSession().getTransaction().commit();
            logger.info("Transaction commited");
        } catch (Exception e) {
            getCurrentSession().getTransaction().rollback();
            logger.warn("Exception occured trying to commit", e);
        } finally {
            getCurrentSession().close();
        }
    }

    public Session getCurrentSession() {
        return sessionFactory.getCurrentSession();
    }

    
    
}
