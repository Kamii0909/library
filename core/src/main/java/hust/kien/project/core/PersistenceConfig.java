package hust.kien.project.core;


import org.hibernate.cfg.AvailableSettings;
import org.springframework.boot.hibernate.autoconfigure.HibernatePropertiesCustomizer;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import hust.kien.project.core.dao.LibraryRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = "hust.kien.project.core", repositoryBaseClass = LibraryRepositoryImpl.class)
public class PersistenceConfig {

    @Bean
    HibernatePropertiesCustomizer hibernatePropertiesCustomizer() {
        return prop -> prop.put(AvailableSettings.CHECK_NULLABILITY, false);
    }
}
