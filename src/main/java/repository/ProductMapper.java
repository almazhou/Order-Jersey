package repository;

import domain.Product;
import org.bson.types.ObjectId;
import org.mongodb.morphia.Datastore;

import java.util.List;

public class ProductMapper implements ProductRepository {
    private Datastore dataStore;

    public ProductMapper(Datastore dataStore) {
        this.dataStore = dataStore;
    }

    @Override
    public List<Product> getAllProducts() {
        return dataStore.find(Product.class).asList();
    }

    @Override
    public Product getProductById(String id) {
        ObjectId objectId = new ObjectId(id);
        return dataStore.get(Product.class,objectId);
    }

    @Override
    public void save(Product product) {
        dataStore.save(product);
    }
}
