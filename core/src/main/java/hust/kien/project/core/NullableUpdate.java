package hust.kien.project.core;

import jakarta.annotation.Nullable;

/**
 * Specifying an update to a new value or to {@code null}, disambiguate from no
 * update (the instance of this class being null).
 */
public record NullableUpdate<T>(@Nullable T value) {

}
