package hust.kien.project.gui.pages.main.components.authors;

import hust.kien.project.core.model.author.Author;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;
import javafx.collections.ObservableList;

public record State(StringProperty name, StringProperty age, ObservableList<Author> authors, BooleanProperty validName,
    BooleanProperty validAge) {
    
}
