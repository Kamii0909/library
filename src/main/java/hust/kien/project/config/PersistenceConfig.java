package hust.kien.project.config;

import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@Configuration
@EnableJpaRepositories(basePackages = "hust.kien.project.dao")
@EnableCaching
public class PersistenceConfig {
    
}
