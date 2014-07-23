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
public class ProductResourceTest extends JerseyTest {
    private final String PROUDCT_ID = "1234567890abcdef90567889";

    private final String PRICING_ID = "1234567890abcdef90567889";
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
        Product product0 = ProductBuilder.buildProduct(PROUDCT_ID, "test");

        Pricing pricing = PriceBuilder.buildPricing(PRICING_ID, 45.0, new DateTime());

        ProductBuilder.buildPricing(product0,pricing);

        when(mockProductRepository.getAllProducts()).thenReturn(Arrays.asList(product0));

        Response response = target("/products").request().get();

        assertThat(response.getStatus(), is(200));

        List result = response.readEntity(List.class);

        Map product = (Map) result.get(0);

        assertThat(product.get("name"), is("test"));

        assertThat(product.get("price"), is("45.0"));

        assertThat(((String) (product.get("uri"))).contains("/products/"+PROUDCT_ID), is(true));
    }


    @Test
    public void should_return_200_for_get_1_product() throws Exception {
        Product product0 = ProductBuilder.buildProduct(PROUDCT_ID, "test");

        Pricing pricing = PriceBuilder.buildPricing(PRICING_ID, 23.0, new DateTime());

        ProductBuilder.buildPricing(product0,pricing);

        when(mockProductRepository.getProductById(PROUDCT_ID)).thenReturn(product0);

        Response response = target("/products/"+PROUDCT_ID).request().get();

        assertThat(response.getStatus(), is(200));

        Map product = response.readEntity(Map.class);

        assertThat(product.get("name"), is("test"));

        assertThat(product.get("price"), is("23.0"));

        assertThat(((String) (product.get("uri"))).contains("/products/"+PROUDCT_ID), is(true));

    }


    @Test
    public void should_return_404_for_get_1_product_failed() throws Exception {

        when(mockProductRepository.getProductById(PROUDCT_ID)).thenThrow(RecordNotFoundException.class);

        Response response = target("/products/" + PROUDCT_ID).request().get();

        assertThat(response.getStatus(), is(404));
    }


    @Test
    public void should_return_201_for_post_one_product() throws Exception {
        Response response = target("/products").request().post(Entity.form(new Form().param("name", "test").param("price", "45")));

        verify(mockProductRepository).save(productArgumentCaptor.capture());

        assertThat(response.getStatus(), is(201));

        assertThat(productArgumentCaptor.getValue().getName(), is("test"));
        System.out.println(productArgumentCaptor.getValue().getPrice().getAmount());
        assertThat(productArgumentCaptor.getValue().getPrice().getAmount(), is(45.0));

    }


    @Test
    public void should_return_200_for_get_all_pricings() throws Exception {
        Pricing pricing = PriceBuilder.buildPricing(PROUDCT_ID, 34, new DateTime(2014, 5, 6, 0, 0));

        Product product_created = ProductBuilder.buildProduct(PROUDCT_ID, "test");

        ProductBuilder.buildPricing(product_created, pricing);

        when(mockProductRepository.getProductById(PROUDCT_ID)).thenReturn(product_created);

        Response response = target("/products/" + PROUDCT_ID + "/pricings").request().get();

        assertThat(response.getStatus(), is(200));

        List list = response.readEntity(List.class);

        Map pricingOne = (Map) list.get(0);

        assertThat(pricingOne.get("price"), is("34.0"));

        assertThat(((String) (pricingOne.get("date"))).contains("2014-05-06"), is(true));

        assertThat(pricingOne.get("productId"), is(PROUDCT_ID));

        String uri = (String) pricingOne.get("uri");
        assertThat(uri.contains("/products/"+PROUDCT_ID+"/pricings/"+PRICING_ID),is(true));

    }


    @Test
    public void should_return_200_for_get_one_pricing() throws Exception {
        Pricing pricing = PriceBuilder.buildPricing(PROUDCT_ID, 34, new DateTime(2014, 5, 6, 0, 0));

        Product product_created = ProductBuilder.buildProduct(PROUDCT_ID, "test");

        ProductBuilder.buildPricing(product_created, pricing);

        when(mockProductRepository.getProductById(PROUDCT_ID)).thenReturn(product_created);

        Response response = target("/products/" + PROUDCT_ID + "/pricings/" + PRICING_ID).request().get();

        assertThat(response.getStatus(), is(200));

        Map pricingOne = response.readEntity(Map.class);

        assertThat(pricingOne.get("price"), is("34.0"));

        assertThat(((String) (pricingOne.get("date"))).contains("2014-05-06"), is(true));

        assertThat(pricingOne.get("productId"), is(PROUDCT_ID));

        String uri = (String) pricingOne.get("uri");
        assertThat(uri.contains("/products/" + PROUDCT_ID + "/pricings/" + PRICING_ID),is(true));

    }


    @Test
    public void should_return_404_for_get_one_pricing() throws Exception {
        Product product_created = ProductBuilder.buildProduct(PROUDCT_ID, "test");

        when(mockProductRepository.getProductById(PROUDCT_ID)).thenReturn(product_created);

        Response response = target("/products/" + PROUDCT_ID + "/pricings/" + PRICING_ID).request().get();

        assertThat(response.getStatus(), is(404));

    }


    @Test
    public void should_return_201_for_post_one_pricing() throws Exception {
        Product product_created = ProductBuilder.buildProduct(PROUDCT_ID, "test");

        when(mockProductRepository.getProductById(PROUDCT_ID)).thenReturn(product_created);

        Response response = target("/products/" + PROUDCT_ID + "/pricings").request().post(Entity.form(new Form().param("amount", "45")));

        assertThat(response.getStatus(), is(201));

        String location = response.getHeaderString("location");

        assertThat(location.contains("/products/" + PROUDCT_ID + "/pricings/"), is(true));


    }
}
