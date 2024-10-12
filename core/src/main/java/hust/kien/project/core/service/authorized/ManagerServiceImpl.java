package hust.kien.project.core.service.authorized;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import hust.kien.project.core.model.auth.LibraryEmployee;
import hust.kien.project.core.service.dynamic.LibraryEmployeeSpecificationBuilder;
import hust.kien.project.core.service.internal.AccountingService;
import hust.kien.project.core.service.internal.LibraryMetadataService;

@Service
public class ManagerServiceImpl implements ManagerService {

    @Autowired
    private LibraryMetadataService metadataService;

    @Autowired
    private AccountingService accountingService;

    private MessageDigest encryptor;

    public ManagerServiceImpl() {
        try {
            encryptor = MessageDigest.getInstance("SHA-256");
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
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

        metadataService.saveOrUpdate(employee);
    }

    @Override
    public long income(LocalDate from, LocalDate to) {
        return accountingService.income(from, to);
    }

    @Override
    public void deleteUser(String username) {
        metadataService.delete(
            metadataService.dynamicFind(
                new LibraryEmployeeSpecificationBuilder().withUsername(username)).get(0));
    }

    @Override
    public boolean isUsernameExist(String username) {
        return !metadataService
            .dynamicFind(new LibraryEmployeeSpecificationBuilder().withUsername(username))
            .isEmpty();
    }

    @Override
    public List<LibraryEmployee> getAllEmployees() {
        return metadataService
            .dynamicFind(new LibraryEmployeeSpecificationBuilder().initCollection().roles().back());
    }

    private byte[] generateRandomSalt(int size) {
        byte[] bytes = new byte[size];
        ThreadLocalRandom.current().nextBytes(bytes);

        return bytes;
    }

}
