package hust.kien.project.controller.component.manager;

import java.time.LocalDate;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import hust.kien.project.service.authorized.ManagerService;
import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.text.Text;
import lombok.extern.slf4j.Slf4j;

@Component
@Lazy
@Slf4j
public class StatisticController {

    @FXML
    private DatePicker startDate, endDate;

    @FXML
    private Text incomeText;

    private final ManagerService managerService;

    public StatisticController(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void initialize() {
        setIncomeText(0);
    }

    private void setIncomeText(Number number) {
        incomeText.setText(number.toString());
    }

    @FXML
    private void handleFindIncome() {
        log.info("Find income request: start date {} end date {}", startDate.getValue(),
            endDate.getValue());

        LocalDate start = startDate.getValue() == null ? LocalDate.now() : startDate.getValue();
        LocalDate end = endDate.getValue() == null ? LocalDate.now() : endDate.getValue();

        setIncomeText(managerService.income(start, end) * 1000);
    }


}
