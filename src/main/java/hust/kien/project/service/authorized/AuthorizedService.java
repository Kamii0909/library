package hust.kien.project.service.authorized;

public interface AuthorizedService {
    <T extends AuthorizedService> Class<T> getRuntimeServiceClass();
}
