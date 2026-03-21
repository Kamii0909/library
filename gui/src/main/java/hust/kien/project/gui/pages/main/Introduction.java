package hust.kien.project.gui.pages.main;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;

import hust.kien.project.gui.pages.InterfaceComponent;
import hust.kien.project.gui.pages.ImmutableNode;
import hust.kien.project.gui.pages.Namespace;
import javafx.fxml.FXMLLoader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;

import hust.kien.project.gui.common.ResourceHelper;
import javafx.concurrent.Worker.State;
import javafx.fxml.FXML;
import javafx.scene.web.WebEngine;
import javafx.scene.web.WebView;

public class Introduction implements InterfaceComponent {

    private final ImmutableNode element;

    @SuppressWarnings("null")
    public Introduction(@Value("classpath:gui/introduction.fxml") Resource fxml, ResourceHelper helper) {
        FXMLLoader loader = helper.load(fxml, this);
        this.element = new ImmutableNode(loader.getRoot());

        WebView webView = Namespace.webView(loader.getNamespace(), "webView");
        WebEngine engine = webView.getEngine();

        engine.loadContent(readFile());
        engine.getLoadWorker().stateProperty().addListener((_, _, newState) -> {
            if (newState == State.SUCCEEDED)
                engine.reload();
        });
    }

    @Override
    public ImmutableNode element() {
        return element;
    }

    private static String readFile() {
        try {
            return Files.readString(new File("gioithieu.txt").toPath());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }
}
