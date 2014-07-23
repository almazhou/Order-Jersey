package domain;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

public class Pricing {

    private double amount;
    private ObjectId id;
    private DateTime modifiedTime;

    public Pricing(double amount) {
        this.amount = amount;
    }

    public Pricing(ObjectId id, double amount, DateTime modifiedTime) {

        this.id = id;
        this.amount = amount;
        this.modifiedTime = modifiedTime;
    }


    public double getAmount() {
        return amount;
    }

    public ObjectId getId() {
        return id;
    }

    public DateTime getModifiedTime() {
        return modifiedTime;
    }
}
