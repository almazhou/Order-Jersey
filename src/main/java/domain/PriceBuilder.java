package domain;

import org.bson.types.ObjectId;
import org.joda.time.DateTime;

public class PriceBuilder {
    public static Pricing buildPricing(String id,double amount,DateTime dateTime){
        ObjectId objectId = new ObjectId(id);
        return new Pricing(objectId,amount,dateTime);
    }
}
