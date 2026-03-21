package hust.kien.project.gui.controller;

import hust.kien.project.core.client.ClientService;
import hust.kien.project.core.client.ClientService.ClientSubscription;
import hust.kien.project.core.client.CurrentSubscription;
import hust.kien.project.core.client.ReadonlyClient;
import hust.kien.project.core.client.Tier;
import hust.kien.project.gui.controller.utils.AlertUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import org.controlsfx.control.CheckComboBox;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
@Lazy
public class ManageClientController {

    private static final Logger log = LoggerFactory.getLogger(ManageClientController.class);

    @FXML
    private TextField nameUI, addressUI;

    @FXML
    private CheckBox isActiveUI;

    @FXML
    private CheckComboBox<Tier> tierUI;

    @FXML
    private TableView<ClientSubscription> clientUI;

    @FXML
    private TableColumn<ClientSubscription, String> nameColumnUI, addressColumnUI, tierColumnUI,
            subscriptionColumnUI;

    private final ClientService clientService;

    private final ObjectProvider<Stage> addClientStageProvider;

    public ManageClientController(ClientService clientService,
                                  @Qualifier("newClientStage") ObjectProvider<Stage> addClientStageProvider) {
        this.clientService = clientService;
        this.addClientStageProvider = addClientStageProvider;
    }

    public void initialize() {

        tierUI.getItems().addAll(List.of(Tier.values()));
        tierUI.getCheckModel().checkAll();
        ObservableValue<String> title = Bindings.concat(
                "Da chon ",
                Bindings.size(tierUI.getCheckModel().getCheckedItems()),
                "/",
                Bindings.size(tierUI.getItems()));
        tierUI.titleProperty().bind(title);

        tierUI.addEventHandler(ComboBoxBase.ON_HIDDEN, ev -> handleFilter());

        clientUI.setRowFactory(param -> {
            TableRow<ClientSubscription> row = new TableRow<>();

            ContextMenu rowMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Chinh sua");
            editItem.setOnAction(_ -> handleEditClient(row.getItem().client()));

            MenuItem removeItem = new MenuItem("Xoa");
            removeItem.setOnAction(_ -> handleDeleteClient(row.getItem().client()));
            rowMenu.getItems().addAll(editItem, removeItem);

            // only display context menu for non-empty rows:
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(rowMenu));
            return row;
        });

        clientUI.setItems(FXCollections.observableArrayList(clientService.allClients()));

        nameColumnUI.setCellValueFactory(
                param -> new ReadOnlyStringWrapper(param.getValue().client().name()));
        addressColumnUI.setCellValueFactory(
                param -> new ReadOnlyStringWrapper(param.getValue().client().address()));
        tierColumnUI.setCellValueFactory(param -> {
            CurrentSubscription subscription = param.getValue().subscription();
            String text = "N/A";

            if (subscription != null) {
                text = subscription.tier().toString();
            }
            return new ReadOnlyStringWrapper(text);

        });
        subscriptionColumnUI.setCellValueFactory(param -> new ReadOnlyStringWrapper(
                param.getValue().subscription() != null ? "Con hop dong" : "Het thoi han"));
    }

    @FXML
    private void handleAddClient() {
        log.info("Add client request");
        Stage stage = addClientStageProvider.getObject();
        stage.show();
        stage.addEventFilter(WindowEvent.WINDOW_CLOSE_REQUEST, ev -> handleFilter());
    }

    @FXML
    private void handleFilter() {
        log.debug("Filtering: name {} address {} subscription {} tier {}", nameUI.getText(),
                addressUI.getText(), isActiveUI.isSelected(), tierUI.getCheckModel().getCheckedItems());

        log.error("Filter client NOT IMPLEMENTED");

//        ClientSpecficationBuilder builder = new ClientSpecficationBuilder();
//        builder.withTierIn(tierUI.getCheckModel().getCheckedItems());
//
//        if (!nameUI.getText().isBlank()) {
//            builder.nameLike(nameUI.getText());
//        }
//
//        if (!addressUI.getText().isBlank()) {
//            builder.addressLike(addressUI.getText());
//        }
//
//        if (isActiveUI.isSelected()) {
//            builder.isActive();
//        }

        // FIXME
        clientUI.setItems(FXCollections.observableList(clientService.allClients()));
    }

    private void handleEditClient(ReadonlyClient client) {
        log.info("Edit client {}", client);
        AlertUtils.showAlert("Tính năng chưa được triển khai do chưa chốt được billing model",
                AlertType.WARNING);
    }

    private void handleDeleteClient(ReadonlyClient client) {
        Optional<ButtonType> confirmation = AlertUtils.showAndWaitOkCancelAlert(
                "Ban co chac chan muon xoa khach hang "
                        + client.name()
                        + " hay khong?");
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            log.info("Deleting client {}", client);
            clientService.delete(client.id());
            clientUI.getItems().removeIf(cs -> cs.client().id().equals(client.id()));
        }
    }
}
