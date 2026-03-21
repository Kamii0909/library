package hust.kien.project.core.client;

import hust.kien.project.core.Utils;
import org.jspecify.annotations.Nullable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Collection;
import java.util.List;

import static hust.kien.project.core.Utils.covarianceCast;

@Service
class ClientServiceImpl implements ClientService {
    private final ClientRepository repository;

    ClientServiceImpl(ClientRepository repository) {
        this.repository = repository;
    }

    @Override
    @Transactional
    public ReadonlyClient create(ClientCreationRequest request) {
        return repository.persist(new Client(request.name(), request.address(), request.phoneNumber()));
    }

    @Override
    @Transactional
    public ReadonlyClient update(ClientUpdateRequest request) {
        ClientId clientId = request.id();
        Client client = findRequiredById(clientId);

        String name = request.name();
        if (name != null) {
            client.setName(name);
        }

        String address = request.address();
        if (address != null) {
            client.setAddress(address);
        }

        String phoneNumber = request.phoneNumber();
        if (phoneNumber != null) {
            client.setPhoneNumber(phoneNumber);
        }

        return client;
    }

    private Client findRequiredById(ClientId clientId) {
        Client client = repository.find(clientId.id());
        if (client == null) {
            throw new IllegalArgumentException("Client " + clientId + " not found");
        }
        return client;
    }

    @Override
    @Transactional
    public ReadonlyClient delete(ClientId id) {
        Client client = findRequiredById(id);

        repository.delete(client);
        return client;
    }

    @Override
    public @Nullable ReadonlyClient findById(ClientId clientId) {
        return repository.find(clientId.id());
    }

    @Override
    public List<ReadonlyClient> findAllByIds(Collection<ClientId> clientIds) {
        List<Long> ids = clientIds.stream().map(ClientId::id).toList();
        return covarianceCast(repository.findAllById(ids));
    }
}
