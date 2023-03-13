package hust.kien.project.controller;

import java.util.List;
import java.util.Optional;
import org.controlsfx.control.CheckComboBox;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import hust.kien.project.controller.utils.AlertUtils;
import hust.kien.project.model.client.Client;
import hust.kien.project.model.client.ClientTier;
import hust.kien.project.service.authorized.LibrarianService;
import hust.kien.project.service.dynamic.ClientSpecficationBuilder;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.beans.value.ObservableValue;
import javafx.collections.FXCollections;
import javafx.fxml.FXML;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ComboBoxBase;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;
import javafx.scene.control.Alert.AlertType;
import javafx.stage.Stage;
import javafx.stage.WindowEvent;
import lombok.extern.slf4j.Slf4j;

@Component
@Lazy
@Slf4j
public class ManageClientController {

    @FXML
    private TextField nameUI, addressUI;

    @FXML
    private CheckBox isActiveUI;

    @FXML
    private CheckComboBox<ClientTier> tierUI;

    @FXML
    private TableView<Client> clientUI;

    @FXML
    private TableColumn<Client, String> nameColumnUI, addressColumnUI, tierColumnUI,
        subscriptionColumnUI;

    private final LibrarianService librarianService;

    private final ObjectProvider<Stage> addClientStageProvider;


    public ManageClientController(LibrarianService librarianService,
        @Qualifier("newClientStage") ObjectProvider<Stage> addClientStageProvider) {
        this.librarianService = librarianService;
        this.addClientStageProvider = addClientStageProvider;
    }

    public void initialize() {

        tierUI.getItems().addAll(List.of(ClientTier.values()));
        tierUI.getCheckModel().checkAll();
        ObservableValue<String> title = Bindings.concat(
            "Da chon ",
            Bindings.size(tierUI.getCheckModel().getCheckedItems()),
            "/",
            Bindings.size(tierUI.getItems()));
        tierUI.titleProperty().bind(title);

        tierUI.addEventHandler(ComboBoxBase.ON_HIDDEN, ev -> handleFilter());

        clientUI.setRowFactory(param -> {
            TableRow<Client> row = new TableRow<>();

            ContextMenu rowMenu = new ContextMenu();
            MenuItem editItem = new MenuItem("Chinh sua");
            editItem.setOnAction(event -> handleEditClient(row.getItem()));

            MenuItem removeItem = new MenuItem("Xoa");
            removeItem.setOnAction(event -> handleDeleteClient(row.getItem()));
            rowMenu.getItems().addAll(editItem, removeItem);

            // only display context menu for non-empty rows:
            row.contextMenuProperty().bind(
                Bindings.when(row.emptyProperty())
                    .then((ContextMenu) null)
                    .otherwise(rowMenu));
            return row;
        });


        clientUI.setItems(FXCollections
            .observableArrayList(librarianService.dynamicFind(new ClientSpecficationBuilder())));
        nameColumnUI.setCellValueFactory(
            param -> new ReadOnlyStringWrapper(param.getValue().getContactInfo().getName()));
        addressColumnUI.setCellValueFactory(
            param -> new ReadOnlyStringWrapper(param.getValue().getContactInfo().getAddress()));
        tierColumnUI.setCellValueFactory(param -> new ReadOnlyStringWrapper(
            param.getValue().getRentInfo().getClientTier().toString()));
        subscriptionColumnUI.setCellValueFactory(param -> new ReadOnlyStringWrapper(
            param.getValue().getRentInfo().getSubscription().isActive() ? "Con hop dong"
                : "Het thoi han"));
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

        ClientSpecficationBuilder builder = new ClientSpecficationBuilder();
        builder.withTierIn(tierUI.getCheckModel().getCheckedItems());

        if (!nameUI.getText().isBlank()) {
            builder.nameLike(nameUI.getText());
        }

        if (!addressUI.getText().isBlank()) {
            builder.addressLike(addressUI.getText());
        }

        if (isActiveUI.isSelected()) {
            builder.isActive();
        }

        clientUI.setItems(FXCollections.observableList(librarianService.dynamicFind(builder)));
    }

    private void handleEditClient(Client client) {
        log.info("Edit client {}", client);
        AlertUtils.showAlert("Tính năng chưa được triển khai do chưa chốt được billing model",
            AlertType.WARNING);
    }

    private void handleDeleteClient(Client client) {
        Optional<ButtonType> confirmation = AlertUtils.showAndWaitOkCancelAlert(
            "Ban co chac chan muon xoa khach hang "
                + client.getContactInfo().getName()
                + " hay khong?");
        if (confirmation.isPresent() && confirmation.get() == ButtonType.OK) {
            log.info("Deleting client {}", client);
            librarianService.delete(client);
            clientUI.getItems().remove(client);
        }
    }
}
