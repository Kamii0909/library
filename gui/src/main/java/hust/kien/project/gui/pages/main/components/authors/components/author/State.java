package hust.kien.project.gui.pages.main.components.authors.components.author;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

record State(StringProperty name, StringProperty age, Runnable onDeleted, BooleanProperty isModifying,
    BooleanProperty isNameValid, BooleanProperty isAgeValid) {
    
}
