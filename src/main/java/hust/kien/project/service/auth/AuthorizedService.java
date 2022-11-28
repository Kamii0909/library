package hust.kien.project.service.auth;

public interface AuthorizedService {
    <T extends AuthorizedService> Class<T> getRuntimeServiceClass();
}
