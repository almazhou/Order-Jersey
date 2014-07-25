package repository;

import database.MongoDb;
import domain.Pricing;
import domain.Product;
import org.junit.After;
import org.junit.Test;
import org.mongodb.morphia.Datastore;

import java.util.List;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertNotNull;
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
        List<Product> allProducts = productMapper.getAllProducts();
        assertThat(allProducts.size(),is(1));
        assertThat(allProducts.get(0).getPrice().getAmount(),is(45.0));
        assertThat(allProducts.get(0).getPricings().size(),is(1));
    }

    @Test
    public void test_get_product_by_id() throws Exception {
        price = new Pricing(45.0);
        Product product = new Product("test", price);
        productMapper.save(product);
        Product productById = productMapper.getProductById(product.getId().toString());
        assertThat(productById.getName(),is("test"));
        assertThat(productById.getPrice().getAmount(),is(45.0));
    }

    @Test
    public void test_save() throws Exception {
        price = new Pricing(45.0);
        Product product = new Product("test", price);
        productMapper.save(product);

        assertNotNull(product.getId());
    }

    @After
    public void tearDown() throws Exception {
        testDataStore.getCollection(Product.class).drop();
    }
}
