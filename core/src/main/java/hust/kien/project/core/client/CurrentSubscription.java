package hust.kien.project.core.client;

import java.time.LocalDate;

public interface CurrentSubscription {
    Tier tier();

    /**
     * The first day of the subscription (inclusive).
     */
    LocalDate start();

    /**
     * The last day of the subscription (inclusive).
     */
    LocalDate end();
}
