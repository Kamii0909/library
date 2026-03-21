package hust.kien.project.core.client;

public record SubscriptionId(Long id) {
    public static SubscriptionId of(Long id) {
        return new SubscriptionId(id);
    }
}
