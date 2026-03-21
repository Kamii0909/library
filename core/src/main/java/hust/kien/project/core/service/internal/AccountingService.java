package hust.kien.project.core.service.internal;

import java.time.LocalDate;

public interface AccountingService {
    
    int income(LocalDate from, LocalDate to);
}
