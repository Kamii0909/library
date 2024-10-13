package hust.kien.project.gui.pages.introduction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.Controller;
import hust.kien.project.gui.pages.DataBinder;
import hust.kien.project.gui.pages.Fxml;
import hust.kien.project.gui.pages.FxmlComponent;
import javafx.scene.web.WebEngine;

public class Introduction extends FxmlComponent<WebContent> {
    
    public Introduction(@Value("classpath:gui/introduction.fxml") Resource fxml, ResourceHelper helper) {
        super(fxml, helper);
    }
    
    @Override
    public Fxml<WebContent, Controller<WebContent>> createFxmlBinding(ApplicationContext context) {
        return new IntroductionFxml(this);
    }
    
    @Override
    public DataBinder<WebContent> binder() {
        return state -> {
            WebEngine engine = state.content().getEngine();
            engine.loadContent(readFile());
            engine.reload();
        };
        
    }
    
    private String readFile() {
        try {
            return Files.readString(new File("gioithieu.txt").toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
