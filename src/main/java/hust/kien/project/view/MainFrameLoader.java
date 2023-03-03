package hust.kien.project.view;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javafx.scene.Scene;
import javafx.scene.layout.Region;
import javafx.stage.Stage;

@Component
@Lazy
public class MainFrameLoader {

    private final String applicationTitle;
    private final double minHeight;
    private final double minWidth;
    private final Scene mainFrameScene;

    MainFrameLoader(
        @Value("${spring.application.ui.mainFrame.title}") String applicationName,
        @Value("${spring.application.ui.mainFrame.min.height}") Double minHeight,
        @Value("${spring.application.ui.mainFrame.min.width}") Double minWidth,
        @Value("${spring.application.ui.mainFrame.pref.height}") Double prefHeight,
        @Value("${spring.application.ui.mainFrame.pref.width}") Double prefWidth,
        @Qualifier("mainFrameNewRegion") ObjectProvider<Region> mainFrameRegionProvider) {

        this.applicationTitle = applicationName;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.mainFrameScene = new Scene(mainFrameRegionProvider.getObject(), prefWidth, prefHeight);
    }

    public void showMainFrame() {
        Stage stage = new Stage();
        
        stage.setTitle(applicationTitle);
        stage.setScene(mainFrameScene);
        stage.setMinHeight(minHeight);
        stage.setMinWidth(minWidth);

        WindowManager.setStage(stage);
        WindowManager.show();
    }

}
