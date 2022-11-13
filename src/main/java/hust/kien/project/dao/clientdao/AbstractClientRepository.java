package hust.kien.project.dao.clientdao;

import java.util.List;
import org.hibernate.SessionFactory;
import hust.kien.project.dao.AbstractGeneralRepository;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;

public class AbstractClientRepository extends AbstractGeneralRepository
    implements ClientLibraryDao {

    protected AbstractClientRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Client findById(Long id) {
        return getCurrentSession().find(Client.class, id);
    }

    @Override
    public List<Client> fetch(int amount) {
        return getCurrentSession().createQuery("from Client c", Client.class).setMaxResults(amount)
            .getResultList();
    }

    @Override
    public void save(Client entity) {
        getCurrentSession().merge(entity);
    }

    @Override
    public void delete(Client entity) {
        getCurrentSession().remove(entity);
    }

    @Override
    public void deleteAll() {
        throw new UnsupportedOperationException("Don't delete all");

    }

    @Override
    public void update(Client entity) {
        getCurrentSession().merge(entity);

    }

    @Override
    public List<Client> findByName(String name) {
        return getCurrentSession()
            .createQuery("from Client c where c.name like :name", Client.class)
            .setParameter("name", "%" + name + "%").getResultList();
    }

    @Override
    public List<Client> findByAddress(String address) {
        return getCurrentSession()
            .createQuery("from Client c where c.address like :address", Client.class)
            .setParameter("address", address).getResultList();
    }

    @Override
    public List<Client> findByTier(ClientTier tier) {
        return getCurrentSession()
            .createQuery("from Client c where c.clientTier = :tier", Client.class)
            .setParameter("tier", tier).getResultList();
    }

}
