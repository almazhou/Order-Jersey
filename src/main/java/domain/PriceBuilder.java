package domain;

import org.joda.time.DateTime;

public class PriceBuilder {
    public static Pricing buildPricing(int id,double amount,DateTime dateTime){
        return new Pricing(id,amount,dateTime);
    }
}
