package hust.kien.project.gui.controller.frame;

import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import javafx.fxml.FXML;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.Region;

@Component
@Lazy
public class ManagerFrameController {

    @FXML
    private GridPane mainContainer;

    private final ObjectProvider<Region> employeeAccountRegionProvider;

    private final ObjectProvider<Region> statisticRegionProvider;

    public ManagerFrameController(
        @Qualifier("employeeAccountRegion") ObjectProvider<Region> employeeAccountRegionProvider,
        @Qualifier("statisticRegion") ObjectProvider<Region> statisticRegionProvider) {
        this.employeeAccountRegionProvider = employeeAccountRegionProvider;
        this.statisticRegionProvider = statisticRegionProvider;
    }

    public void initialize() {
        mainContainer.add(statisticRegionProvider.getObject(), 0, 0);
        mainContainer.add(employeeAccountRegionProvider.getObject(), 1, 0);
    }
}
