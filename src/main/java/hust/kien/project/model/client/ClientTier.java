package hust.kien.project.model.client;

public enum ClientTier {
    FIRST_CLASS(10, 20), 
    NORMAL(3, 10);

    private int maximumCanBorrow;
    private int monthlyCost;

    public int getMaximumCanBorrow() {
        return this.maximumCanBorrow;
    }

    public int getMonthlyCost() {
        return this.monthlyCost;
    }

    private ClientTier(final int maximumCanBorrow, final int monthlyCost) {
        this.maximumCanBorrow = maximumCanBorrow;
        this.monthlyCost = monthlyCost;
    }
}
