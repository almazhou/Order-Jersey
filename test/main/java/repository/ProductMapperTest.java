package repository;

import database.MongoDb;
import domain.Pricing;
import domain.Product;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;

public class ProductMapperTest {

    private Datastore testDataStore = MongoDb.createDataStore("test");
    private ProductMapper productMapper = new ProductMapper(testDataStore);
    private Pricing price;


    @Test
    public void test_get_all_products() throws Exception {
        price = new Pricing(45.0);
        Product product = new Product("test", price);
        productMapper.save(product);

        assertThat(product.getId(),is(1));
    }

    @Test
    public void test_get_product_by_id() throws Exception {

    }

    @Test
    public void test_save() throws Exception {

    }
}
