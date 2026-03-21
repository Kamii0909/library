package hust.kien.project.core.client;

import org.jspecify.annotations.Nullable;

public class ClientUpdateRequest {
    private final ClientId id;
    private @Nullable String name;
    private @Nullable String address;
    private @Nullable String phoneNumber;

    public ClientUpdateRequest(ClientId id) {
        this.id = id;
    }

    public ClientId id() {
        return id;
    }

    public @Nullable String name() {
        return name;
    }

    public void setName(@Nullable String name) {
        this.name = name;
    }

    public @Nullable String address() {
        return address;
    }

    public void setAddress(@Nullable String address) {
        this.address = address;
    }

    public @Nullable String phoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(@Nullable String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
