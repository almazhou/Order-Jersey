package domain;

import java.util.ArrayList;
import java.util.List;

public class Product {
    private int id;
    private String name;
    private Pricing price;
    private List<Pricing> pricings = new ArrayList<Pricing>();

    public Product(String name, Pricing price) {
        this.name = name;
        this.price = price;
        pricings.add(price);
    }

    public Product() {

    }

    public Product(String name, int id) {

        this.name = name;
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public Pricing getPrice() {
        return price;
    }

    public List<Pricing> getPricings() {
        return pricings;
    }

    public void setPrice(Pricing pricing) {
        price = pricing;
        pricings.add(pricing);
    }
}
