package hust.kien.project.gui.pages.login;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.Component;
import hust.kien.project.gui.pages.DataBinder;
import hust.kien.project.gui.pages.FxmlComponent;
import hust.kien.project.gui.pages.Page;

public class LoginPage extends FxmlComponent<State> implements Page {
    
    public LoginPage(@Value("classpath:gui/login.fxml") Resource fxml, ResourceHelper helper) {
        super(fxml, helper);
    }
    
    @Override
    public LoginFxml createFxmlBinding(ApplicationContext context) {
        return new LoginFxml(this, context);
    }
    
    @Override
    public Component<?> component() {
        return this;
    }
    
    @Override
    public DataBinder<State> binder() {
        return new RememberMeBinder();
    }
}
