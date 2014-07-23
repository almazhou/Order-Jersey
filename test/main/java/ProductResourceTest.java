import domain.PriceBuilder;
import domain.Pricing;
import domain.Product;
import domain.ProductBuilder;
import exception.RecordNotFoundException;
import exception.RecordNotFoundExceptionHandler;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.joda.time.DateTime;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.ProductRepository;
import resources.ProductResource;

import javax.ws.rs.client.Entity;
import javax.ws.rs.core.Application;
import javax.ws.rs.core.Form;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;



@RunWith(MockitoJUnitRunner.class)
public class ProductResourceTest extends JerseyTest{
    @Mock
    ProductRepository mockProductRepository;

    @Captor
    ArgumentCaptor<Product> productArgumentCaptor;

    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockProductRepository).to(ProductRepository.class);
            }
        };

        return new ResourceConfig().register(ProductResource.class).register(binder).register(RecordNotFoundExceptionHandler.class);
    }

    @Test
    public void should_return_200_for_get_all_products() throws Exception {

        Product product0 = ProductBuilder.buildProduct(1, "test");
        when(mockProductRepository.getAllProducts()).thenReturn(Arrays.asList(product0));

        Response response = target("/products").request().get();

        assertThat(1,is(1));

        assertThat(response.getStatus(),is(200));

        List result = response.readEntity(List.class);

        Map product = (Map) result.get(0);

        assertThat(product.get("name"), is("test"));

        assertThat(product.get("price"),is("23.0"));

        assertThat(((String)(product.get("uri"))).contains("/products/1"),is(true));
    }


    @Test
    public void should_return_200_for_get_1_product() throws Exception {
        Product product0 = ProductBuilder.buildProduct(1, "test");

        when(mockProductRepository.getProductById(1)).thenReturn(product0);

        Response response = target("/products/1").request().get();

        assertThat(response.getStatus(),is(200));

        Map product = response.readEntity(Map.class);

        assertThat(product.get("name"), is("test"));

        assertThat(product.get("price"),is("23.0"));

        assertThat(((String)(product.get("uri"))).contains("/products/1"),is(true));

    }


    @Test
    public void should_return_404_for_get_1_product_failed() throws Exception {

        when(mockProductRepository.getProductById(1)).thenThrow(RecordNotFoundException.class);

        Response response = target("/products/1").request().get();

        assertThat(response.getStatus(),is(404));
    }


    @Test
    public void should_return_201_for_post_one_product() throws Exception {
        Response response = target("/products").request().post(Entity.form(new Form().param("name", "test").param("price", "45")));

        verify(mockProductRepository).save(productArgumentCaptor.capture());

        assertThat(response.getStatus(), is(201));

        assertThat(productArgumentCaptor.getValue().getName(),is("test"));
        assertThat(productArgumentCaptor.getValue().getPrice(),is(45.0));

    }


    @Test
    public void should_return_200_for_get_all_pricings() throws Exception {
        Pricing pricing = PriceBuilder.buildPricing(1, 34, new DateTime(2014, 5, 6, 0, 0));

        Product product_created = ProductBuilder.buildProduct(1, "test");

        ProductBuilder.buildPricing(product_created,pricing);

        when(mockProductRepository.getProductById(1)).thenReturn(product_created);

        Response response = target("/products/1/pricings").request().get();

        assertThat(response.getStatus(),is(200));

        List list = response.readEntity(List.class);

        Map pricingOne = (Map) list.get(0);

        assertThat(pricingOne.get("price"),is("34.0"));

        assertThat(((String)(pricingOne.get("date"))).contains("2014-05-06"),is(true));

        assertThat(pricingOne.get("productId"),is("1"));

        assertThat(pricingOne.get("uri"),is("/products/1/pricings/1"));

    }
}
