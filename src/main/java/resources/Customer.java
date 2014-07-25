package resources;

public class Customer {
    private String name;
    private String address;

    public Customer(String address, String name) {
        this.address = address;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getAddress() {
        return address;
    }
}
