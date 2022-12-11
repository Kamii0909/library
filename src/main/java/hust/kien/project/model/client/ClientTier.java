package hust.kien.project.model.client;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum ClientTier {
    FIRST_CLASS(10, 20), NORMAL(3, 10);

    private int maximumCanBorrow;

    private int monthlyCost;
}
