package hust.kien.project.gui.view.event;

import hust.kien.project.core.author.Author;
import javafx.event.Event;
import javafx.event.EventType;

public class AuthorDeletedEvent extends Event {

    public static final EventType<AuthorDeletedEvent> AUTHOR_DELETED_EVENT =
        new EventType<>("Author deleted");

    private final transient Author deletedAuthor;

    public Author getDeletedAuthor() {
        return deletedAuthor;
    }

    public AuthorDeletedEvent(Author deletedAuthor) {
        super(AUTHOR_DELETED_EVENT);
        this.deletedAuthor = deletedAuthor;
    }

}
