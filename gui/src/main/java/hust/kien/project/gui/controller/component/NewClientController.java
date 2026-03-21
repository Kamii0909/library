package hust.kien.project.gui.controller.component;

import hust.kien.project.core.client.ClientCreationRequest;
import hust.kien.project.core.client.ClientService;
import hust.kien.project.core.client.Tier;
import hust.kien.project.gui.controller.utils.AlertUtils;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;

import static hust.kien.project.gui.controller.component.BookComponentUtils.setElementBorderFromValidationResult;

public class NewClientController {
    
    private static final Logger log = LoggerFactory.getLogger(NewClientController.class);

    private final ClientService clientService;
    
    @FXML
    private TextField nameUI, addressUI;
    
    @FXML
    private DatePicker dateStartUI, dateEndUI;
    
    @FXML
    private ChoiceBox<Tier> tierUI;


    public NewClientController(ClientService clientService) {
        this.clientService = clientService;
    }

    public void initialize() {
        tierUI.setItems(FXCollections.observableList(List.of(Tier.values())));
        tierUI.getSelectionModel().select(Tier.NORMAL);
        dateStartUI.setValue(LocalDate.now());
        dateEndUI.setValue(LocalDate.now());
    }
    
    @FXML
    private boolean handleValidation() {
        return setElementBorderFromValidationResult(nameUI, !nameUI.getText().isBlank()) &&
                setElementBorderFromValidationResult(addressUI, !addressUI.getText().isBlank());
    }
    
    @FXML
    private void handleAddNewClient() {
        log.info("Add new client");
        
        if (!handleValidation()) {
            return;
        }
        
        if (dateStartUI.getValue().isAfter(dateEndUI.getValue())) {
            Alert alert = AlertUtils.createAlert("Ngay bat dau khong duoc truoc ngay ket thuc",
                    AlertType.ERROR);
            alert.initOwner(nameUI.getScene().getWindow());
            alert.show();
            return;
        }
        ClientCreationRequest request = new ClientCreationRequest(
                nameUI.getText(),
                addressUI.getText(),
                "Not implemented");
        
        log.info("Saving client: {}", request);
        clientService.create(request);
        
        Window window = nameUI.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
