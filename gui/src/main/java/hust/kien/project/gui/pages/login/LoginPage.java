package hust.kien.project.gui.pages.login;

import java.util.function.Supplier;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import com.google.common.base.Suppliers;

import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.Page;
import javafx.scene.Parent;
import javafx.scene.layout.Region;

@Login
class LoginPage implements Page {
    
    private final Supplier<Region> rootNode;
    
    public LoginPage(@Value("classpath:gui/login.fxml") Resource fxml, ResourceHelper helper) { 
        this.rootNode = Suppliers.memoize(() -> helper.loadFxml(fxml));
    }
    
    @Override
    public Parent show() {
        return this.rootNode.get();
    }
}
