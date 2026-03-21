package hust.kien.project.core.service.authorized;

import hust.kien.project.core.auth.EmployeeService;
import hust.kien.project.core.auth.LibraryEmployee;
import hust.kien.project.core.service.internal.AccountingService;
import org.springframework.stereotype.Service;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@Service
public class ManagerServiceImpl implements ManagerService {

    private final EmployeeService metadataService;
    private final AccountingService accountingService;

    private final MessageDigest encryptor;

    public ManagerServiceImpl(EmployeeService metadataService, AccountingService accountingService) {
        this.metadataService = metadataService;
        this.accountingService = accountingService;
        try {
            encryptor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            throw new AssertionError(e);
        }
    }

    @Override
    @SuppressWarnings("unchecked")
    public Class<ManagerService> getRuntimeServiceClass() {
        return ManagerService.class;
    }

    @Override
    public void createUser(LibraryEmployee employee, String password) {
        employee.setSalt(generateRandomSalt(2));
        /** (int) (Math.random() * 10) */

        encryptor.update(password.getBytes());
        encryptor.update(employee.getSalt());

        employee.setEncryptedPassword(encryptor.digest());

        metadataService.create(employee);
    }

    @Override
    public long income(LocalDate from, LocalDate to) {
        return accountingService.income(from, to);
    }

    @Override
    public void deleteUser(String username) {
        metadataService.delete(username);
    }

    @Override
    public boolean isUsernameExist(String username) {
        return metadataService.findById(username) != null;
    }

    @Override
    public List<LibraryEmployee> getAllEmployees() {
        return metadataService.all();
    }

    private byte[] generateRandomSalt(int size) {
        byte[] bytes = new byte[size];
        ThreadLocalRandom.current().nextBytes(bytes);

        return bytes;
    }

}
