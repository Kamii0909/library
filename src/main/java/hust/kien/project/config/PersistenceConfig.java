package hust.kien.project.config;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import hust.kien.project.dao.LibraryRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = "hust.kien.project.dao", repositoryBaseClass = LibraryRepositoryImpl.class)
public class PersistenceConfig {

}
