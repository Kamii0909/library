package hust.kien.project.gui.pages.main.components.authors;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.FxmlComponent;

public class AuthorsComponent extends FxmlComponent<@NonNull State, @NonNull Interactions> {
    
    public AuthorsComponent(
        @Value("classpath:gui/manage_author_new.fxml") Resource fxml,
        ResourceHelper helper) {
        
        super(fxml, helper);
    }
    
    @Override
    public Fxml createFxmlBinding(ApplicationContext context) {
        return new Fxml(this, context);
    }
    
}
