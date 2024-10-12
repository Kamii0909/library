package hust.kien.project.core.dao.analyze;

import java.time.LocalDate;
import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import hust.kien.project.core.model.client.Client;
import hust.kien.project.core.model.client.ClientRentInfo;
import hust.kien.project.core.model.client.ClientRentInfo_;
import hust.kien.project.core.model.client.ClientTier;
import hust.kien.project.core.model.client.Client_;
import hust.kien.project.core.model.client.Subscription;
import hust.kien.project.core.model.client.Subscription_;
import jakarta.persistence.Tuple;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Expression;
import jakarta.persistence.criteria.Path;
import jakarta.persistence.criteria.Root;

@Repository
public class AccountingRepositoryImpl implements AccountingRepository {

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public int income(LocalDate from, LocalDate to) {
        int result = 0;

        // boilerplate
        Session session = sessionFactory.openSession();
        session.beginTransaction();

        // String hql = """
        // select c.rentInfo.clientTier, count(c.id)
        // from Client c
        // group by c.rentInfo.clientTier
        // where c.rentInfo.subscription.startDate >= :from
        // and c.rentInfo.subscription.endDate <= :end
        // """;

        // more boilerplate
        CriteriaBuilder cb = sessionFactory.getCriteriaBuilder();
        CriteriaQuery<Tuple> cq = cb.createQuery(Tuple.class);
        Root<Client> root = cq.from(Client.class);

        Path<ClientRentInfo> rentInfo = root.get(Client_.rentInfo);
        Path<Subscription> subscription = rentInfo.get(ClientRentInfo_.subscription);

        cq.groupBy(rentInfo.get(ClientRentInfo_.clientTier));

        cq.where(
            cb.greaterThanOrEqualTo(subscription.get(Subscription_.startDate), from),
            cb.lessThanOrEqualTo(subscription.get(Subscription_.endDate), to));

        Expression<ClientTier> tier = rentInfo.get(ClientRentInfo_.clientTier);
        Expression<Long> count = cb.count(root.get(Client_.id));

        cq.multiselect(tier, count);


        List<Tuple> list = session.createQuery(cq).getResultList();

        for (Tuple tuple : list) {
            result += tuple.get(tier).getMonthlyCost() * tuple.get(count);
        }

        session.getTransaction().commit();
        session.close();
        return result;
    }


}
