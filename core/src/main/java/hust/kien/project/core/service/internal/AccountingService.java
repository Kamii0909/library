package hust.kien.project.core.service.internal;

import java.time.LocalDate;

public interface AccountingService {
    
    public int income(LocalDate from, LocalDate to);
}
