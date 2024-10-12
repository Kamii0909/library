package hust.kien.project.gui.controller;

import java.time.LocalDate;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;

import hust.kien.project.core.model.ticket.ActiveTicket;
import hust.kien.project.core.model.ticket.ClosedTicket;
import hust.kien.project.core.model.ticket.LocalDateConverter;
import hust.kien.project.core.model.ticket.Ticket;
import hust.kien.project.core.service.authorized.AuditService;
import hust.kien.project.core.service.authorized.LibrarianService;
import hust.kien.project.core.service.dynamic.ticket.AbstractTicketSpecificationBuilder;
import hust.kien.project.core.service.dynamic.ticket.ActiveTicketSpecificationBuilder;
import hust.kien.project.core.service.dynamic.ticket.ClosedTicketSpecificationBuilder;
import hust.kien.project.core.service.dynamic.ticket.TicketSpecificationBuilder;
import hust.kien.project.gui.controller.utils.AlertUtils;
import javafx.beans.binding.Bindings;
import javafx.beans.property.ReadOnlyStringWrapper;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.CheckBox;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.DatePicker;
import javafx.scene.control.MenuItem;
import javafx.scene.control.TableColumn;
import javafx.scene.control.TableRow;
import javafx.scene.control.TableView;
import javafx.scene.control.TextField;

@Component
@Lazy
public class ManageTicketController {
    
    private static final Logger log = LoggerFactory.getLogger(ManageTicketController.class);
    
    @FXML
    private TextField bookNameUI, clientNameUI;
    
    @FXML
    private DatePicker startDateUI, endDateUI;
    
    @FXML
    private CheckBox closedTicketUI, activeTicketUI;
    
    @FXML
    private TableView<Ticket> ticketUI;
    
    @FXML
    private TableColumn<Ticket, String> bookColumnUI, clientColumnUI, startDateColumnUI,
            endDateColumnUI;
    
    @Autowired
    private LibrarianService librarianService;
    
    @Autowired
    private AuditService auditService;
    
    public void initialize() {
        ticketUI.setRowFactory(param -> {
            TableRow<Ticket> row = new TableRow<>();
            
            MenuItem edit = new MenuItem("Chinh sua");
            edit.setOnAction(ev -> doEditTicket(row.getItem()));
            MenuItem delete = new MenuItem("Xoa");
            delete.setOnAction(ev -> doDeleteTicket(row.getItem()));
            
            ContextMenu menu = new ContextMenu(edit, delete);
            
            row.contextMenuProperty().bind(
                    Bindings.when(row.emptyProperty())
                            .then((ContextMenu) null)
                            .otherwise(menu));
            
            return row;
        });
        ticketUI.setItems(FXCollections
                .observableList(librarianService.dynamicFind(new TicketSpecificationBuilder().init())));
        
        bookColumnUI.setCellValueFactory(
                param -> new ReadOnlyStringWrapper(param.getValue().getBook().getBookInfo().getName()));
        clientColumnUI.setCellValueFactory(
                param -> new ReadOnlyStringWrapper(
                        param.getValue().getClient().getContactInfo().getName()));
        startDateColumnUI.setCellValueFactory(
                param -> new ReadOnlyStringWrapper(param.getValue().getStartDate().toString()));
        endDateColumnUI.setCellValueFactory(
                param ->
                {
                    String result;
                    if (param.getValue() instanceof ClosedTicket) {
                        ClosedTicket ticket = (ClosedTicket) param.getValue();
                        result = ticket.getEndDate().toString();
                    } else {
                        result = "Chua ket thuc";
                    }
                    return new ReadOnlyStringWrapper(result);
                });
    }
    
    @FXML
    @SuppressWarnings("unchecked")
    private void handleFilter() {
        log.info("Filtering");
        AbstractTicketSpecificationBuilder<?> builder;
        switch ((activeTicketUI.isSelected() ? 1 : 0) + (closedTicketUI.isSelected() ? 2 : 0)) {
        case 0:
            return;
        
        case 1:
            builder = new ActiveTicketSpecificationBuilder();
            break;
        
        case 2:
            builder = new ClosedTicketSpecificationBuilder();
            break;
        
        case 3:
            builder = new TicketSpecificationBuilder();
            break;
        
        default:
            throw new IllegalStateException(
                    this.getClass().getSimpleName() + " handleFilter switch");
        }
        if (!bookNameUI.getText().isBlank()) {
            builder.bookNameLike(bookNameUI.getText());
        }
        
        if (!clientNameUI.getText().isBlank()) {
            builder.bookNameLike(clientNameUI.getText());
        }
        
        LocalDate start =
                startDateUI.getValue() != null ? startDateUI.getValue() : LocalDateConverter.MIN;
        
        LocalDate end =
                endDateUI.getValue() != null ? endDateUI.getValue() : LocalDateConverter.MAX;
        
        if (builder instanceof ClosedTicketSpecificationBuilder) {
            ((ClosedTicketSpecificationBuilder) builder)
                    .endDateBetween(LocalDateConverter.MIN, end);
        } else {
            builder.startDateBetween(start, end);
        }
        
        ticketUI.setItems((ObservableList<Ticket>) FXCollections
                .observableList(librarianService.dynamicFind(builder.init())));
    }
    
    @FXML
    private void handleAddTicket() {
        log.info("Add ticket request");
        AlertUtils.showAlert("Tính năng chưa được triển khai do chưa chốt được Billing model", AlertType.WARNING);
    }
    
    private void doDeleteTicket(Ticket ticket) {
        log.info("Deleting ticket {}", ticket);
        
        Optional<ButtonType> result =
                AlertUtils.showAndWaitOkCancelAlert("Ban co chac muon xoa phieu muon nay khong?");
        if (result.isPresent() && result.get() == ButtonType.OK) {
            if (ticket instanceof ActiveTicket) {
                ticket = librarianService.closeActiveTicket((ActiveTicket) ticket);
            }
            auditService.deleteTicket(ticket);
            handleFilter();
        }
    }
    
    private void doEditTicket(Ticket ticket) {
        log.info("Edit ticket {}", ticket);
        AlertUtils.showAlert("Tinh nang dang duoc xem xet lai", AlertType.WARNING);
    }
}
