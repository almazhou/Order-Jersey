package domain;

import org.bson.types.ObjectId;
import resources.Customer;

public class CustomerBuilder {
    public static Customer buildCustomerWithId(ObjectId id, String name, String address){
        return new Customer(id,name,address);
    }
}
