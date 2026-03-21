package hust.kien.project.core.client;

public record ClientId(Long id) {
    public static ClientId of(Long id) {
        return new ClientId(id);
    }
}
