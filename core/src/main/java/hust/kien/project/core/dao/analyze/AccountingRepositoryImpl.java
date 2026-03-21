package hust.kien.project.core.dao.analyze;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;

@Repository
public class AccountingRepositoryImpl implements AccountingRepository {

    private static final Logger log = LoggerFactory.getLogger(AccountingRepositoryImpl.class);

    private final SessionFactory sessionFactory;

    public AccountingRepositoryImpl(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }


    //    @Override
//    public int income(LocalDate from, LocalDate to) {
//        int result = 0;
//
//        // boilerplate
//        Session session = sessionFactory.openSession();
//        session.beginTransaction();
//
//        // String hql = """
//        // select c.rentInfo.clientTier, count(c.id)
//        // from Client c
//        // group by c.rentInfo.clientTier
//        // where c.rentInfo.subscription.startDate >= :from
//        // and c.rentInfo.subscription.endDate <= :end
//        // """;
//
//        // more boilerplate
//        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
//        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
//        Root<Client> root = cq.from(Client.class);
//
//        Path<ClientRentInfo> rentInfo = root.get(Client_.rentInfo);
//        Path<Subscription2> subscription = rentInfo.get(ClientRentInfo_.subscription);
//
//        cq.groupBy(rentInfo.get(ClientRentInfo_.clientTier));
//
//        cq.where(
//            cb.greaterThanOrEqualTo(subscription.get(Subscription_.startDate), from),
//            cb.lessThanOrEqualTo(subscription.get(Subscription_.endDate), to));
//
//        Expression<Tier> tier = rentInfo.get(ClientRentInfo_.clientTier);
//        Expression<Long> count = cb.count(root.get(Client_.id));
//
//        cq.multiselect(tier, count);
//
//
//        List<Tuple> list = session.createQuery(cq).getResultList();
//
//        for (Tuple tuple : list) {
//            result += tuple.get(tier).getMonthlyCost() * tuple.get(count);
//        }
//
//        session.getTransaction().commit();
//        session.close();
//        return result;
//    }


    @Override
    public int income(LocalDate from, LocalDate to) {
        log.error("NOT IMPLEMENTED");
        return 0;
    }
}
