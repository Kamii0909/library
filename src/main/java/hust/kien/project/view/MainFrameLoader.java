package hust.kien.project.view;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javafx.scene.Scene;
import javafx.scene.layout.Region;

@Component
@Lazy
public class MainFrameLoader {

    private final String applicationTitle;
    private final double minHeight;
    private final double minWidth;
    private final double prefHeight;
    private final double prefWidth;
    private final Scene mainFrameScene;

    MainFrameLoader(
        @Value("${spring.application.ui.mainFrame.title}") String applicationName,
        @Value("${spring.application.ui.mainFrame.min.height}") Double minHeight,
        @Value("${spring.application.ui.mainFrame.min.width}") Double minWidth,
        @Value("${spring.application.ui.mainFrame.pref.height}") Double prefHeight,
        @Value("${spring.application.ui.mainFrame.pref.width}") Double prefWidth,
        @Qualifier("mainFrameRegion") Region mainFrameRegion) {

        this.applicationTitle = applicationName;
        this.minHeight = minHeight;
        this.minWidth = minWidth;
        this.prefHeight = prefHeight;
        this.prefWidth = prefWidth;
        this.mainFrameScene = new Scene(mainFrameRegion);
    }

    public void showMainFrame() {
        WindowManager.setScene(
            mainFrameScene, applicationTitle,
            prefHeight, prefWidth,
            minHeight, minWidth);
    }

}
