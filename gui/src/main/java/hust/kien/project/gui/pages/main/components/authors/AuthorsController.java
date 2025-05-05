package hust.kien.project.gui.pages.main.components.authors;

import static hust.kien.project.gui.controller.component.BookComponentUtils.isIntegerValid;

import java.util.List;

import org.jspecify.annotations.NonNull;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import hust.kien.project.core.author.Author;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.core.service.dynamic.AuthorSpecificationBuilder;
import hust.kien.project.gui.pages.BaseController;
import javafx.beans.property.BooleanProperty;
import javafx.beans.property.StringProperty;

class AuthorsController extends BaseController<@NonNull State, @NonNull Interactions> implements Interactions {
    private static final Logger log = LoggerFactory.getLogger(AuthorsController.class);
    
    private final LibrarianService librarianService;
    
    AuthorsController(State state, LibrarianService librarianService) {
        super(state);
        state.authors().setAll(librarianService.dynamicFind(new AuthorSpecificationBuilder()));
        this.librarianService = librarianService;
    }
    
    @Override
    public Interactions interactions() {
        return this;
    }
    
    @Override
    public void handleFilter() {
        State state = state();
        
        String name = state.name().get();
        String age = state.age().get();
        
        BooleanProperty isNameValid = state.validName();
        BooleanProperty isAgeValid = state.validAge();
        
        log.debug("Filter request name: {} age: {} ", name, age);
        
        isNameValid.set(true);
        if (!age.isBlank()) {
            boolean ageValid = isIntegerValid(age);
            isAgeValid.set(ageValid);
            if (!ageValid)
                return;
        }
        
        AuthorSpecificationBuilder builder = new AuthorSpecificationBuilder();
        
        if (!name.isEmpty()) {
            builder.nameContains(name);
        }
        if (!age.isBlank()) {
            int i = Integer.parseInt(age);
            builder.ageBetween(i, i);
        }
        
        List<Author> authors = librarianService.dynamicFind(builder);
        
        state.authors().setAll(authors);
    }
    
    private static boolean isValidInteger(String year) {
        try {
            Integer.parseInt(year);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
    
    @Override
    public void handleAddAuthor() {
        State state = state();
        
        StringProperty nameProp = state.name();
        StringProperty ageProp = state.age();
        
        String name = nameProp.get();
        String age = ageProp.get();
        
        boolean isNameValid = !name.isBlank();
        boolean isAgeValid = isValidInteger(age);
        
        state.validName().set(isNameValid);
        state.validAge().set(isAgeValid);
        
        if (!isAgeValid || !isNameValid)
            return;
        
        Author author = new Author(name, Integer.parseInt(age));
        
        log.info("Adding author {}", author);
        librarianService.saveOrUpdate(author);
        
        nameProp.set("");
        ageProp.set("");
        
        state.authors().setAll(librarianService.dynamicFind(new AuthorSpecificationBuilder()));
    }
    
}
