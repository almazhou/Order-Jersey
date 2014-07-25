package domain;

import exception.RecordNotFoundException;
import org.bson.types.ObjectId;
import org.mongodb.morphia.annotations.Entity;
import org.mongodb.morphia.annotations.Id;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.stream.Collectors;
@Entity("products")
public class Product {
    @Id
    private ObjectId id;
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

    public Product(String name, ObjectId objectId) {
        this.name = name;
        this.id = objectId;
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

    public ObjectId getId() {
        return id;
    }

    public Pricing getPriceById(String priceId) {
        List<Pricing> findList = pricings.stream().filter(new Predicate<Pricing>() {
            @Override
            public boolean test(Pricing pricing) {
                return pricing.getId().toString().equals(priceId);
            }
        }).collect(Collectors.toList());

        if(findList.size() == 0 || findList.get(0) == null){
            throw new RecordNotFoundException();
        }
        return findList.get(0);
    }
}
