package hust.kien.project.controller.component.manager;

import static hust.kien.project.controller.utils.AlertUtils.showAndWaitOkCancelAlert;
import static javafx.collections.FXCollections.observableList;
import java.util.Arrays;
import org.controlsfx.control.CheckComboBox;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import hust.kien.project.controller.utils.AlertUtils;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.model.auth.LibraryRole;
import hust.kien.project.service.authorized.ManagerService;
import javafx.beans.binding.Bindings;
import javafx.fxml.FXML;
import javafx.scene.control.Alert.AlertType;
import javafx.scene.control.ButtonType;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.ListCell;
import javafx.scene.control.ListView;
import javafx.scene.control.MenuItem;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import lombok.extern.slf4j.Slf4j;

@Component
@Lazy
@Slf4j
public class EmployeeController {
    @FXML
    private TextField nameField, usernameField;

    @FXML
    private PasswordField passwordField, repasswordField;

    @FXML
    private CheckComboBox<LibraryRole> rolesField;

    @FXML
    private ListView<LibraryEmployee> employeeAccountContainer;

    private final ManagerService managerService;

    public EmployeeController(ManagerService managerService) {
        this.managerService = managerService;
    }

    public void initialize() {
        rolesField.getItems().addAll(LibraryRole.values());
        employeeAccountContainer
            .setItems(observableList(managerService.getAllEmployees()));
        employeeAccountContainer.setCellFactory(param -> {
            ListCell<LibraryEmployee> cell = new ListCell<>() {
                @Override
                public void updateItem(LibraryEmployee employee, boolean empty) {
                    super.updateItem(employee, empty);
                    if (employee == null || empty) {
                        setText(null);
                        setGraphic(null);
                    } else {
                        setText(employee.getEmployeeName() + ": " + employee.getUsername() + " - "
                            + Arrays.asList(employee.getRoles()));
                    }
                }
            };

            MenuItem delete = new MenuItem("Xóa");
            delete.setOnAction(ev -> deleteEmployee(cell.getItem()));

            ContextMenu menu = new ContextMenu(delete);
            cell.contextMenuProperty().bind(
                Bindings.when(cell.emptyProperty())
                    .then((ContextMenu) null)
                    .otherwise(menu));
            return cell;
        });
    }

    @FXML
    private void handleAddEmployee() {
        log.info("Adding employee");

        // validate first
        if (validateAndShowAlert()) {
            if (managerService.isUsernameExist(usernameField.getText())) {
                ButtonType result = showAndWaitOkCancelAlert("Tên đăng nhập đã tồn tại, ghi đè?")
                    .orElse(ButtonType.CANCEL);
                if (result == ButtonType.CANCEL) {
                    return;
                }
            }

            LibraryEmployee employee = LibraryEmployee.builder()
                .employeeName(nameField.getText())
                .username(usernameField.getText())
                .roles(rolesField.getCheckModel().getCheckedItems())
                .build();
            managerService.createUser(employee, passwordField.getText());
            employeeAccountContainer.setItems(observableList(managerService.getAllEmployees()));
        }
    }

    private void deleteEmployee(LibraryEmployee employee) {
        showAndWaitOkCancelAlert(
            "Bạn có chắc chắn muốn xóa nhân viên "
                + employee.getEmployeeName()
                + " không?")
                    .filter(bt -> bt == ButtonType.OK)
                    .ifPresent(ok -> doDeleteEmployeeAndRefresh(employee));
    }

    private void doDeleteEmployeeAndRefresh(LibraryEmployee emp) {
        log.info("Deleting employee: {}", emp);
        managerService.deleteUser(emp.getUsername());
        employeeAccountContainer
            .setItems(observableList(managerService.getAllEmployees()));
    }

    private boolean validateAndShowAlert() {
        if (nameField.getText().isEmpty()) {
            failValidation("Tên nhân viên không được bỏ trống");
            return false;
        } else if (usernameField.getText().isEmpty()) {
            failValidation("Tên đăng nhập không được bỏ trống");
            return false;
        } else if (passwordField.getText().length() < 8) {
            failValidation("Mật khẩu tối thiểu 8 kí tự");
            return false;
        } else if (!passwordField.getText().equals(repasswordField.getText())) {
            failValidation("Nhập lại mật khẩu không đúng");
            return false;
        }
        return true;
    }

    private void failValidation(String error) {
        AlertUtils.showAlert(error, AlertType.ERROR);
    }
}
