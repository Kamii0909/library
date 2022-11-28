package hust.kien.project.dao.client;

import java.time.LocalDate;

public interface AccountingRepository {
    int income(LocalDate from, LocalDate to);
}
