package hust.kien.project.gui.view.event;

import hust.kien.project.core.author.ReadonlyAuthor;
import javafx.event.Event;
import javafx.event.EventType;

public class AuthorDeletedEvent extends Event {

    public static final EventType<AuthorDeletedEvent> AUTHOR_DELETED_EVENT =
        new EventType<>("Author deleted");

    private final transient ReadonlyAuthor deletedAuthor;

    public ReadonlyAuthor getDeletedAuthor() {
        return deletedAuthor;
    }

    public AuthorDeletedEvent(ReadonlyAuthor deletedAuthor) {
        super(AUTHOR_DELETED_EVENT);
        this.deletedAuthor = deletedAuthor;
    }

}
