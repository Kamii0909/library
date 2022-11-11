package hust.kien.project.dao.clientdao;

import java.util.List;
import java.util.Optional;
import hust.kien.project.dao.LibraryDao;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;

public interface ClientLibraryDao extends LibraryDao<Client, Long> {
    //-------------------------
    // FIND BY CLIENT CONTACT INFO
    //-------------------------
    public List<Client> findByName(String name);
    public Optional<Client> findAnyByName(String name);

    public List<Client> findByAddress(String address);
    public Optional<Client> findAnyByAddress(String address);


    //-------------------------
    // FIND BY CLIENT RENT INFO
    //-------------------------
    public List<Client> findByTier(ClientTier tier);
    public Optional<Client> findAnyByTier(ClientTier tier);
}
