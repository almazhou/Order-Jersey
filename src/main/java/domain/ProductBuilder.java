package domain;

import org.bson.types.ObjectId;

public class ProductBuilder {

    public static Product buildProduct(String id, String name) {
        ObjectId objectId = new ObjectId(id);
        return new Product(name,objectId);
    }

    public static Product buildPricing(Product product, Pricing pricing){
        product.addPrice(pricing);
        return product;
    }

}
