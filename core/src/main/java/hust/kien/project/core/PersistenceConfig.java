package hust.kien.project.core;


import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

import hust.kien.project.core.dao.LibraryRepositoryImpl;

@Configuration
@EnableJpaRepositories(basePackages = "hust.kien.project.core.dao", repositoryBaseClass = LibraryRepositoryImpl.class)
public class PersistenceConfig {

}
