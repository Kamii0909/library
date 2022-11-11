package hust.kien.project.dao.clientdao;

import java.util.List;
import java.util.Optional;
import org.hibernate.SessionFactory;
import hust.kien.project.dao.AbstractGeneralRepository;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;

public class AbstractClientRepository extends AbstractGeneralRepository implements ClientLibraryDao {

    protected AbstractClientRepository(SessionFactory sessionFactory) {
        super(sessionFactory);
    }

    @Override
    public Client findById(Long id) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public List<Client> fetch(int amount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public void save(Client entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void delete(Client entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void deleteAll() {
        // TODO Auto-generated method stub
        
    }

    @Override
    public void update(Client entity) {
        // TODO Auto-generated method stub
        
    }

    @Override
    public List<Client> findByName(String name) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Client> findAnyByName(String name) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<Client> findByAddress(String address) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Client> findAnyByAddress(String address) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }

    @Override
    public List<Client> findByTier(ClientTier tier) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Optional<Client> findAnyByTier(ClientTier tier) {
        // TODO Auto-generated method stub
        return Optional.empty();
    }
    
}
