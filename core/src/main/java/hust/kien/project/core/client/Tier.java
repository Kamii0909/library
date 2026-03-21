package hust.kien.project.core.client;

public enum Tier {
    FIRST_CLASS(10, 20),
    NORMAL(3, 10);

    private final int maximumCanBorrow;
    private final int monthlyCost;

    Tier(final int maximumCanBorrow, final int monthlyCost) {
        this.maximumCanBorrow = maximumCanBorrow;
        this.monthlyCost = monthlyCost;
    }

    public int maximumCanBorrow() {
        return this.maximumCanBorrow;
    }

    public int monthlyCost() {
        return this.monthlyCost;
    }
}
