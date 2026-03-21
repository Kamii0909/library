package hust.kien.project.core.dao.analyze;

import java.time.LocalDate;

public interface AccountingRepository {
    int income(LocalDate from, LocalDate to);
}
