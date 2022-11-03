package hust.kien.project;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.Metadata;
import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.model.naming.ImplicitNamingStrategyJpaCompliantImpl;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.service.ServiceRegistry;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;

public class AppTest {
    static SessionFactory sessionFactory;
    static Session session;


    @BeforeAll
    static void init() {
        ServiceRegistry registry = new StandardServiceRegistryBuilder().configure().build();

        Metadata metadata =
            new MetadataSources(registry).getMetadataBuilder()
                                         .applyImplicitNamingStrategy(ImplicitNamingStrategyJpaCompliantImpl.INSTANCE)
                                         .build();

        try {
            sessionFactory = metadata.getSessionFactoryBuilder().build();
        } catch (Exception e) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

    @AfterAll
    static void destroy() {
        sessionFactory.close();
    }


}
