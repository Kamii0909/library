package hust.kien.project.model.client;

public enum ClientTier {
    FIRST_CLASS (10, 20) ,
    NORMAL (3, 10);

    private int canBorrow;

    private int monthlyCost;

    ClientTier(int canBorrow, int monthlyCost) {
        this.canBorrow = canBorrow;
        this.monthlyCost = monthlyCost;
    }

        public int getCanBorrow() {
        return canBorrow;
    }

    public int getMonthlyCost() {
        return monthlyCost;
    }
}
