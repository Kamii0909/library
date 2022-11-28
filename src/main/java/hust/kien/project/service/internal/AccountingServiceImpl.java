package hust.kien.project.service.internal;

import java.time.LocalDate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import hust.kien.project.dao.client.ClientRepository;

@Service
@Transactional
public class AccountingServiceImpl implements AccountingService {

    @Autowired
    private ClientRepository clientRepository;

    @Override
    public int income(LocalDate from, LocalDate to) {
        return clientRepository.income(from, to);
    }
    
}
