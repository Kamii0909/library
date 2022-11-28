package hust.kien.project.service.auth;

import java.time.LocalDate;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.model.auth.LibraryEmployee;
import hust.kien.project.service.dynamic.LibraryUserSpecificationBuilder;
import hust.kien.project.service.internal.AccountingService;
import hust.kien.project.service.internal.LibraryMetadataService;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private LibraryMetadataService metadataService;

    @Autowired
    private AccountingService accountingService;

    @Override
    @SuppressWarnings("unchecked")
    public Class<ManagerService> getRuntimeServiceClass() {
        return ManagerService.class;
    }

    @Override
    public LibraryEmployee createUser(LibraryEmployee employee) {
        return metadataService.saveOrUpdate(employee);
    }

    @Override
    public List<LibraryEmployee> findEmployeeFromName(String employeeName) {
        return metadataService
            .dynamicFind(new LibraryUserSpecificationBuilder().employeeNameContains(employeeName));
    }

    @Override
    public void deleteUser(LibraryEmployee employee) {
        metadataService.delete(employee);
    }

    @Override
    public long income(LocalDate from, LocalDate to) {
        return accountingService.income(from, to);
    }

}
