package domain;

import exception.RecordNotFoundException;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;

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

    public void addPrice(Pricing pricing) {
        price = pricing;
        pricings.add(pricing);
    }

    public Pricing getPriceById(int priceId) {
        List<Pricing> findList = pricings.stream().filter(new Predicate<Pricing>() {
            @Override
            public boolean test(Pricing pricing) {
                return pricing.getId() == priceId;
            }
        }).collect(Collectors.toList());

        if(findList.size() == 0 || findList.get(0) == null){
            throw new RecordNotFoundException();
        }
        return findList.get(0);
    }
}
