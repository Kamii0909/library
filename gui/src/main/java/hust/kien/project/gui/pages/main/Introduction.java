package hust.kien.project.gui.pages.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import org.jspecify.annotations.NonNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import hust.kien.project.gui.pages.BasicComponent;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.layout.Region;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Introduction extends BasicComponent<@NonNull Introduction> {

    @FXML
    private WebView webView;

    private final Region element;

    @SuppressWarnings("null")
    public Introduction(@Value("classpath:gui/introduction.fxml") Resource fxml, ResourceHelper helper) {
        this.element = helper.loadFxml(fxml, this);
    }

    @Override
    public Region element() {
        return element;
    }

    @FXML
    public void initialize() {
        WebEngine engine = webView.getEngine();

        engine.loadContent(readFile());
        engine.getLoadWorker().stateProperty().addListener((_, _, newState) -> {
            if (newState == State.SUCCEEDED)
                engine.reload();
        });
    }

    private static String readFile() {
        try {
            return Files.readString(new File("gioithieu.txt").toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
