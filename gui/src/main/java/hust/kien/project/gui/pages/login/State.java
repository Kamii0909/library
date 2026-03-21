package hust.kien.project.gui.pages.login;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

record State(StringProperty username, StringProperty password, BooleanProperty rememberMe) {
    
}
