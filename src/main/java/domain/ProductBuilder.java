package domain;

public class ProductBuilder {

    public static Product buildProduct(int id, String name) {
        return new Product(name,id);
    }

    public static Product buildPricing(Product product, Pricing pricing){
        product.addPrice(pricing);

        return product;
    }

}
