package hust.kien.project.core.client;

import org.jspecify.annotations.Nullable;

import java.util.Collection;
import java.util.List;
import java.util.Map;

public interface ClientService {

    ReadonlyClient create(ClientCreationRequest request);

    ReadonlyClient update(ClientUpdateRequest request);

    ReadonlyClient delete(ClientId id);

    @Nullable
    ReadonlyClient findById(ClientId clientId);

    List<ReadonlyClient> findAllByIds(Collection<ClientId> clientIds);

    default Map<ClientId, CurrentSubscription> currentSubscriptionsOf(Collection<ClientId> clientIds) {
        return Map.of();
    }

    record ClientSubscription(ReadonlyClient client, @Nullable CurrentSubscription subscription) {
    }

    default List<ClientSubscription> activeClients() {
        return List.of();
    }

    default List<ClientSubscription> allClients() {
        return List.of();
    }
}
