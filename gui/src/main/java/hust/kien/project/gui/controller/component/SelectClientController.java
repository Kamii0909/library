package hust.kien.project.gui.controller.component;

import com.google.common.collect.Maps;
import hust.kien.project.core.book.ReadonlyBook;
import hust.kien.project.core.client.ClientId;
import hust.kien.project.core.client.ClientService;
import hust.kien.project.core.client.ClientService.ClientSubscription;
import hust.kien.project.core.client.CurrentSubscription;
import hust.kien.project.core.client.ReadonlyClient;
import hust.kien.project.core.service.ClientOverlimitException;
import hust.kien.project.core.service.OutOfStockException;
import hust.kien.project.core.ticket.Ticket;
import hust.kien.project.core.ticket.TicketService;
import hust.kien.project.gui.controller.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.stream.Collectors;

public class SelectClientController {

    private static final Logger log = LoggerFactory.getLogger(SelectClientController.class);

    @FXML
    private ListView<String> clientList;

    @FXML
    private TextField clientFilterText;

    private Map<String, ?> allObjectsMap;

    private final ReadonlyBook book;

    private final boolean isBorrow;

    public SelectClientController(ReadonlyBook book, boolean isBorrow) {
        this.book = book;
        this.isBorrow = isBorrow;
    }

    public void initialize() {
        if (isBorrow) {
            allObjectsMap = clientService.activeClients()
                    .stream()
                    .collect(Collectors.toMap(this::createClientString, Function.identity()));
        } else {

            List<Ticket> activeTickets = ticketService.activeTicketsOf(book.id());

            List<ClientId> clientIds = activeTickets.stream().map(Ticket::clientId).toList();
            List<ReadonlyClient> fetchedClients = clientService.findAllByIds(clientIds);

            Map<ClientId, CurrentSubscription> subscriptions = clientService.currentSubscriptionsOf(clientIds);

            Map<ClientId, ReadonlyClient> clients = Maps.uniqueIndex(fetchedClients, ReadonlyClient::id);

            allObjectsMap = activeTickets.stream()
                    .collect(Collectors.toMap(at -> {
                                ClientId clientId = at.clientId();
                                ReadonlyClient client = Objects.requireNonNull(clients.get(clientId));
                                CurrentSubscription currentSubscription = subscriptions.get(clientId);
                                return createClientString(new ClientSubscription(client, currentSubscription));
                            },
                            Function.identity(), (_, newVal) -> newVal));
        }

        clientList.getItems().setAll(allObjectsMap.keySet());
    }

    private String createClientString(ClientSubscription client) {
        // FIXME
        if (isBorrow) {
            assert client.subscription() != null;
            return String.format("%s(Da muon %s/Co the muon %d)", client.client().name(),
                    "N/A",
                    client.subscription().tier().maximumCanBorrow());
        } else {
            return String.format("%s", client.client().name());
        }

    }

    private ClientService clientService;

    private TicketService ticketService;

    @FXML
    private void handleCompleted() {
        Window window = clientList.getScene().getWindow();

        if (isBorrow) {
            handleBorrow(window);
        } else {
            handleReturn(window);
        }

    }

    private void handleBorrow(Window window) {
        try {
            ClientSubscription client =
                    (ClientSubscription) allObjectsMap.get(clientList.getSelectionModel().getSelectedItem());
            if (client != null) {
                ticketService.createTicket(book.id(), client.client().id(), LocalDate.now());
            }
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        } catch (OutOfStockException e) {
            AlertUtils.showAlert("Sach da het hang trong kho", AlertType.ERROR);
            ((Stage) window).close();
        } catch (ClientOverlimitException e) {
            AlertUtils.showAlert("Khach hang can tra sach truoc khi muon them", AlertType.ERROR);
            ((Stage) window).close();
        }
    }

    private void handleReturn(Window window) {
        Ticket activeTicket =
                (Ticket) allObjectsMap.get(clientList.getSelectionModel().getSelectedItem());
        if (activeTicket != null) {
            ticketService.closeTicket(activeTicket.id());
            window.fireEvent(new WindowEvent(window, WindowEvent.WINDOW_CLOSE_REQUEST));
        } else {
            ((Stage) window).close();
        }
    }

    @FXML
    private void handleFilterByName() {
        log.info("Handle filter by name: {}", clientFilterText.getText());
    }
}
