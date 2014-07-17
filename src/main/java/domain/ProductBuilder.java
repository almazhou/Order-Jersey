package domain;

public class ProductBuilder {
    public static Product buildProduct(int id, String name, double price) {

        return new Product(name, price, id);
    }
}
