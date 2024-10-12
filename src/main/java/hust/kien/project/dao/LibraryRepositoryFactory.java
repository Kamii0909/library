package hust.kien.project.dao;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import org.springframework.context.annotation.DependsOn;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@NoRepositoryBean
@Component
@SuppressWarnings({ "unchecked", "null" })
@DependsOn("org.springframework.boot.autoconfigure.orm.jpa.HibernateJpaAutoConfiguration")
public class LibraryRepositoryFactory {
    
    private Map<Class<?>, LibraryRepository<?, ?>> repositories;
    
    public LibraryRepositoryFactory(Set<LibraryRepository<?, ?>> repositories) {
        this.repositories = repositories
                .stream()
                .collect(
                        Collectors.toMap(
                                repo -> GenericTypeResolver.resolveTypeArguments(repo.getClass(),
                                        LibraryRepository.class)[0],
                                Function.identity()));
    }
    
    @SuppressWarnings("java:S1452")
    public <T> LibraryRepository<T, ?> getRepository(Class<T> clazz) {
        return (LibraryRepository<T, ?>) repositories.get(clazz);
    }
}
