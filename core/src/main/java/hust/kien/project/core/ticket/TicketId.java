package hust.kien.project.core.ticket;

public record TicketId(Long id) {
    public static TicketId of(Long id) {
        return new TicketId(id);
    }
}
