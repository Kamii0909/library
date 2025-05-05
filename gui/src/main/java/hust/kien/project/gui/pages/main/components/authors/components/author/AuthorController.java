package hust.kien.project.gui.pages.main.components.authors.components.author;

import static hust.kien.project.gui.controller.component.BookComponentUtils.isIntegerValid;

import java.time.LocalDate;
import java.util.Optional;

import org.jspecify.annotations.Nullable;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.author.AuthorService;
import hust.kien.project.core.author.AuthorUpdateRequest;
import hust.kien.project.gui.controller.component.BookComponentUtils;
import hust.kien.project.gui.controller.utils.AlertUtils;
import hust.kien.project.gui.pages.Controller;
import javafx.scene.control.ButtonType;

class AuthorController implements Controller<State, Interactions>, Interactions {
    private static final Logger log = LoggerFactory.getLogger(AuthorController.class);

    private final AuthorService authorService;

    private final State state;

    private Author author;

    public AuthorController(AuthorService authorService, State state, Author author) {
        this.authorService = authorService;
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
            authorService.delete(author);
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
            AuthorUpdateRequest request = validateAndRevertFields();
            if (request != null) {
                commitAuthor(request);
            }
        }
    }

    private @Nullable AuthorUpdateRequest validateAndRevertFields() {
        String newName = state.name().get();
        String newAgeString = state.age().get();

        if (BookComponentUtils.isIntegerValid(newAgeString) && !newName.isBlank()) {

            int newAge = Integer.parseInt(newAgeString);
            AuthorUpdateRequest request = new AuthorUpdateRequest(author.id());
            request.setName(newName);
            request.setDob(LocalDate.now().minusYears(newAge));

            return request;
        }

        setNameAndAgeField(author.name(), author.age());
        validate();
        return null;
    }

    private void setNameAndAgeField(String name, int age) {
        state.name().set(name);
        state.age().set(Integer.toString(age));
    }

    private void commitAuthor(AuthorUpdateRequest request) {
        log.info("Saving author: {}", author);
        author = authorService.update(request);
    }

    @Override
    public void validate() {
        state.isAgeValid().set(isIntegerValid(state.age().get()));
        state.isNameValid().set(!state.name().get().isBlank());
    }

}
