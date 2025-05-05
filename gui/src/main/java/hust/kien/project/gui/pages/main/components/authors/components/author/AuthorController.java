package hust.kien.project.gui.pages.main.components.authors.components.author;

import static hust.kien.project.gui.controller.component.BookComponentUtils.isIntegerValid;

import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.gui.controller.component.BookComponentUtils;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.pages.Controller;
import javafx.scene.control.ButtonType;

class AuthorController implements Controller<State, Interactions>, Interactions {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);
    
    private final LibrarianService librarianService;
    
    private final State state;
    
    private Author author;
    
    public AuthorController(LibrarianService librarianService, State state, Author author) {
        this.librarianService = librarianService;
        this.state = state;
        this.author = author;
        state.name().set(author.name());
        state.age().set(Integer.toString(author.age()));
    }
    
    @Override
    public State state() {
        return state;
    }
    
    @Override
    public Interactions interactions() {
        return this;
    }
    
    @Override
    public void handleDeleteAuthor() {
        Optional<ButtonType> response = AlertUtils.showAndWaitOkCancelAlert("Are you sure?");
        
        if (response.isEmpty() || response.get() == ButtonType.CANCEL) {
            // Do nothing
        } else if (response.get() == ButtonType.OK) {
            librarianService.delete(author);
            state.onDeleted().run();
            log.info("Delete author request: {{}}", author);
        } else {
            throw new IllegalStateException("can't be here, should be only OK Cancel and Close");
        }
    }
    
    @Override
    public void handleModifyAuthor() {
        log.info("Modify author request {}", author);
        if (state.isModifying().get()) {
            // modifyButton.setText("Hoan tat");
        } else {
            // modifyButton.setText("Sua thong tin");
            if (validateAndRevertFields()) {
                commitAuthor();
            }
        }
    }
    
    private boolean validateAndRevertFields() {
        if (BookComponentUtils.isIntegerValid(state.age().get()) && !state.name().get().isBlank()) {
            author.setName(state.name().get());
            author.setAge(Integer.parseInt(state.age().get()));
            return true;
        } else {
            setNameAndAgeField(author.name(), author.age());
            validate();
            return false;
        }
    }
    
    private void setNameAndAgeField(String name, int age) {
        state.name().set(name);
        state.age().set(Integer.toString(age));
    }
    
    private void commitAuthor() {
        log.info("Saving author: {}", author);
        author = librarianService.saveOrUpdate(author);
    }
    
    @Override
    public void validate() {
        state.isAgeValid().set(isIntegerValid(state.age().get()));
        state.isNameValid().set(!state.name().get().isBlank());
    }
    
}
