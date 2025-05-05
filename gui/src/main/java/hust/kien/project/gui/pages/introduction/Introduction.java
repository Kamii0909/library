package hust.kien.project.gui.pages.introduction;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.FxmlComponent;

public class Introduction extends FxmlComponent<@NonNull WebContent, @NonNull Void> {
    
    public Introduction(@Value("classpath:gui/introduction.fxml") Resource fxml, ResourceHelper helper) {
        super(fxml, helper);
    }
    
    @Override
    public IntroductionFxml createFxmlBinding(ApplicationContext context) {
        return new IntroductionFxml(this);
    }
}
