package resources;

import domain.Order;
import org.bson.types.ObjectId;

public class Customer {
    private String name;
    private String address;
    private Order order;
    private ObjectId id;

    public Customer(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public Customer(ObjectId id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }

    public ObjectId getId() {
        return id;
    }

}
