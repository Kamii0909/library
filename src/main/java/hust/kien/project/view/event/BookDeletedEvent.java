package hust.kien.project.view.event;

import hust.kien.project.model.book.Book;
import javafx.event.Event;
import javafx.event.EventType;

public class BookDeletedEvent extends Event {

    public static final EventType<Event> BOOK_DELETED_EVENT = new EventType<>("Book deleted");

    private final transient Book deletedBook;

    public Book getDeletedBook() {
        return deletedBook;
    }

    public BookDeletedEvent(Book deletedBook) {
        super(BOOK_DELETED_EVENT);
        this.deletedBook = deletedBook;
    }

}
