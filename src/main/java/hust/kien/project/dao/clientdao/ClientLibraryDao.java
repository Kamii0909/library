package hust.kien.project.dao.clientdao;

import java.util.List;
import hust.kien.project.dao.LibraryDao;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;

public interface ClientLibraryDao extends LibraryDao<Client, Long> {
    //-------------------------
    // FIND BY CLIENT CONTACT INFO
    //-------------------------
    public List<Client> findByName(String name);

    public List<Client> findByAddress(String address);


    //-------------------------
    // FIND BY CLIENT RENT INFO
    //-------------------------
    public List<Client> findByTier(ClientTier tier);
}
