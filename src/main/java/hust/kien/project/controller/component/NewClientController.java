package hust.kien.project.controller.component;

import static hust.kien.project.controller.component.BookComponentUtils.setElementBorderFromValidationResult;

import java.time.LocalDate;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hust.kien.project.controller.utils.AlertUtils;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;
import hust.kien.project.service.authorized.LibrarianService;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ChoiceBox;
import javafx.scene.control.DatePicker;
import javafx.scene.control.TextField;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class NewClientController {
    
    private static final Logger log = LoggerFactory.getLogger(NewClientController.class);
    
    @FXML
    private TextField nameUI, addressUI;
    
    @FXML
    private DatePicker dateStartUI, dateEndUI;
    
    @FXML
    private ChoiceBox<ClientTier> tierUI;
    
    @Autowired
    private LibrarianService librarianService;
    
    public void initialize() {
        tierUI.setItems(FXCollections.observableList(List.of(ClientTier.values())));
        tierUI.getSelectionModel().select(ClientTier.NORMAL);
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
        Client client = Client.builder()
                .name(nameUI.getText()).address(addressUI.getText())
                .startDate(dateStartUI.getValue()).endDate(dateEndUI.getValue())
                .tier(tierUI.getSelectionModel().getSelectedItem())
                .build();
        
        log.info("Saving client: {}", client);
        librarianService.saveOrUpdate(client);
        
        Window window = nameUI.getScene().getWindow();
        window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
    }
}
