package hust.kien.project.core;

import java.util.List;
import java.util.Objects;

public final class Utils {
    private Utils() {
    }

    public static <T> T idRequired(T id) {
        return Objects.requireNonNull(id, "This object is not persisted");
    }


    public static <S, T extends S> List<S> covarianceCast(List<T> books) {
        @SuppressWarnings({"unchecked", "rawtypes"})
        List<S> result = (List) books;
        return result;
    }
}
