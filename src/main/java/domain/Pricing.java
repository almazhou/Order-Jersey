package domain;

import org.joda.time.DateTime;

public class Pricing {

    private double amount;
    private int id;
    private DateTime modifiedTime;

    public Pricing(double amount) {
        this.amount = amount;
    }

    public Pricing(int id, double amount, DateTime modifiedTime) {

        this.id = id;
        this.amount = amount;
        this.modifiedTime = modifiedTime;
    }


    public double getAmount() {
        return amount;
    }

    public int getId() {
        return id;
    }

    public DateTime getModifiedTime() {
        return modifiedTime;
    }
}
