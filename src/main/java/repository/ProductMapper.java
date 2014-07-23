package repository;

import domain.Product;
import org.mongodb.morphia.Datastore;

import java.util.List;

public class ProductMapper implements ProductRepository {
    private Datastore dataStore;

    public ProductMapper(Datastore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<Product> getAllProducts() {
        return null;
    }

    @Override
    public Product getProductById(String id) {
        return null;
    }

    @Override
    public void save(Product product) {
        dataStore.save(product);
        System.out.println(product.getId());
    }
}
