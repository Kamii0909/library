package hust.kien.project.core.dao.client;

import java.util.List;

import hust.kien.project.core.dao.LibraryRepository;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.client.ClientTier;

public interface ClientRepository extends LibraryRepository<Client, Long> {

    public List<Client> findByContactInfo_Name(String name);

    public List<Client> findByContactInfo_NameLikeIgnoreCase(String name);

    public List<Client> findByContactInfo_AddressLikeIgnoreCase(String address);

    public List<Client> findByRentInfo_ClientTier(ClientTier clientTier);
}
