package repository;

import domain.Product;

import java.util.List;

public interface ProductRepository {
    public List<Product> getAllProducts();

    Product getProductById(String id);

    void save(Product product);
}
