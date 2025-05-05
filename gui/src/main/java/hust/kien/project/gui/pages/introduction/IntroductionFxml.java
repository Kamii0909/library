package hust.kien.project.gui.pages.introduction;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jspecify.annotations.NonNull;

import hust.kien.project.gui.pages.BaseController;
import hust.kien.project.gui.pages.Controller;
import hust.kien.project.gui.pages.FxmlBinding;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

class IntroductionFxml extends FxmlBinding<@NonNull WebContent, @NonNull Void> {
    
    @FXML
    private WebView webView;
    
    @SuppressWarnings("null")
    IntroductionFxml(Introduction page) {
        super(page);
    }
    
    @Override
    public WebContent bind() {
        return new WebContent(webView);
    }
    
    @Override
    public @NonNull Controller<@NonNull WebContent, @NonNull Void> createController() {
        return new BaseController<WebContent, Void>(bind()) {
            {
                WebEngine engine = state().content().getEngine();
                
                engine.loadContent(readFile());
                engine.getLoadWorker().stateProperty().addListener((obs, oldState, newState) -> {
                    if (newState == State.SUCCEEDED)
                        engine.reload();
                });
            }
            
            @Override
            public Void interactions() {
                throw new UnsupportedOperationException("This controller can't handle any interactions");
            }
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
