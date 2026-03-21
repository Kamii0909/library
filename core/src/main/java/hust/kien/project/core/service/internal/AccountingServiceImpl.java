package hust.kien.project.core.service.internal;

import hust.kien.project.core.dao.analyze.AccountingRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
public class AccountingServiceImpl implements AccountingService {

    private final AccountingRepository accountingRepository;

    public AccountingServiceImpl(AccountingRepository accountingRepository) {
        this.accountingRepository = accountingRepository;
    }

    @Override
    public int income(LocalDate from, LocalDate to) {
        return accountingRepository.income(from, to);
    }

}
