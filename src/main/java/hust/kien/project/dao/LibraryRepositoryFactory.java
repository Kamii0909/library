package hust.kien.project.dao;

import java.util.Map;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

@NoRepositoryBean
@Component
@SuppressWarnings({"unchecked", "null",})
public class LibraryRepositoryFactory {

    private Map<Class<?>, LibraryRepository<?, ?>> repositories;

    @Autowired
    public LibraryRepositoryFactory(Set<LibraryRepository<?, ?>> repositories) {
        this.repositories =
            repositories
                .stream()
                .collect(
                    Collectors.toMap(
                        repo -> GenericTypeResolver.resolveTypeArguments(repo.getClass(),
                            LibraryRepository.class)[0],
                        Function.identity()));
    }

    public <T> LibraryRepository<T, ?> getRepository(Class<T> clazz) {
        return (LibraryRepository<T, ?>) repositories.get(clazz);
    }
}
