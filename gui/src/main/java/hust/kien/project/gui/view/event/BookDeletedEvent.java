package hust.kien.project.gui.view.event;

import hust.kien.project.core.book.ReadonlyBook;
import javafx.event.Event;
import javafx.event.EventType;

public class BookDeletedEvent extends Event {

    public static final EventType<Event> BOOK_DELETED_EVENT = new EventType<>("Book deleted");

    private final transient ReadonlyBook deletedBook;

    public ReadonlyBook getDeletedBook() {
        return deletedBook;
    }

    public BookDeletedEvent(ReadonlyBook deletedBook) {
        super(BOOK_DELETED_EVENT);
        this.deletedBook = deletedBook;
    }

}
