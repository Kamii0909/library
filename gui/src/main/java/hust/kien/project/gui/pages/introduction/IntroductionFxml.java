package hust.kien.project.gui.pages.introduction;

import hust.kien.project.gui.pages.AbstractController;
import hust.kien.project.gui.pages.Controller;
import hust.kien.project.gui.pages.Fxml;
import javafx.fxml.FXML;
import javafx.scene.layout.VBox;
import javafx.scene.web.WebView;

class IntroductionFxml extends Fxml<WebContent, Controller<WebContent>> {
    
    @FXML
    private VBox vBox;
    
    @FXML
    private WebView webView;
    
    IntroductionFxml(Introduction page) {
        super(page);
    }
    
    @Override
    public Controller<WebContent> createController() {
        return new AbstractController<WebContent>(new WebContent(webView)) {};
    }
}
