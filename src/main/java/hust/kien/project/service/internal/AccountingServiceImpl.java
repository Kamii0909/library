package hust.kien.project.service.internal;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import hust.kien.project.dao.analyze.AccountingRepository;

@Service
public class AccountingServiceImpl implements AccountingService {

    @Autowired
    private AccountingRepository accountingRepository;

    @Override
    public int income(LocalDate from, LocalDate to) {
        return accountingRepository.income(from, to);
    }
    
}
