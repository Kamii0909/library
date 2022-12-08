package hust.kien.project.dao.client;

import java.util.List;
import hust.kien.project.dao.LibraryRepository;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;

public interface ClientRepository extends LibraryRepository<Client, Long> {

    public List<Client> findByContactInfo_Name(String name);

    public List<Client> findByContactInfo_NameLikeIgnoreCase(String name);

    public List<Client> findByContactInfo_AddressLikeIgnoreCase(String address);

    public List<Client> findByRentInfo_ClientTier(ClientTier clientTier);
}
