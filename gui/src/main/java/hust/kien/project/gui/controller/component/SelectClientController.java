package hust.kien.project.gui.controller.component;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import hust.kien.project.core.model.book.Book;
import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.service.ClientOverlimitException;
import hust.kien.project.core.service.OutOfStockException;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.core.service.dynamic.ClientSpecficationBuilder;
import hust.kien.project.core.service.dynamic.ticket.ActiveTicketSpecificationBuilder;
import hust.kien.project.gui.controller.utils.AlertUtils;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ListView;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import javafx.stage.Window;
import javafx.stage.WindowEvent;

public class SelectClientController {
    
    private static final Logger log = LoggerFactory.getLogger(SelectClientController.class);
    
    @FXML
    private ListView<String> clientList;
    
    @FXML
    private TextField clientFilterText;
    
    private Map<String, ?> allObjectsMap;
    
    private final Book book;
    
    private final boolean isBorrow;
    
    public SelectClientController(Book book, boolean isBorrow) {
        this.book = book;
        this.isBorrow = isBorrow;
    }
    
    public void initialize() {
        if (isBorrow) {
            allObjectsMap = librarianService.dynamicFind(
                    new ClientSpecficationBuilder().isActive()
                            .initCollection()
                            .activeTickets()
                            .back())
                    .stream()
                    .collect(Collectors.toMap(this::createClientString, Function.identity()));
        } else {
            
            List<ActiveTicket> activeTickets =
                    librarianService.dynamicFind(new ActiveTicketSpecificationBuilder().book(book));
            
            allObjectsMap = activeTickets.stream()
                    .collect(Collectors.toMap(at -> createClientString(at.getClient()),
                            Function.identity(), (oldVal, newVal) -> newVal));
        }
        
        clientList.getItems().setAll(allObjectsMap.keySet());
    }
    
    private String createClientString(Client client) {
        if (isBorrow) {
            return String.format("%s(Da muon %d/Co the muon %d)", client.getContactInfo().getName(),
                    client.getRentInfo().getActiveTickets().size(),
                    client.getRentInfo().getClientTier().getMaximumCanBorrow());
        } else {
            return String.format("%s", client.getContactInfo().getName());
        }
        
    }
    
    @Autowired
    private LibrarianService librarianService;
    
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
            Client client =
                    (Client) allObjectsMap.get(clientList.getSelectionModel().getSelectedItem());
            if (client != null) {
                librarianService.createActiveTicket(book, client);
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
        ActiveTicket activeTicket =
                (ActiveTicket) allObjectsMap.get(clientList.getSelectionModel().getSelectedItem());
        if (activeTicket != null) {
            librarianService.closeActiveTicket(activeTicket);
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
