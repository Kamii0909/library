package hust.kien.project.core.service.authorized;

public interface AuthorizedService {
    <T extends AuthorizedService> Class<T> getRuntimeServiceClass();
}
