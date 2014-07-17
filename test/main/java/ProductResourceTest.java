import domain.Product;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.ProductRepository;
import resources.ProductResource;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;


@RunWith(MockitoJUnitRunner.class)
public class ProductResourceTest extends JerseyTest{
    @Mock
    ProductRepository mockProductRepository;
    @Override
    protected Application configure() {
        AbstractBinder binder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockProductRepository).to(ProductRepository.class);
            }
        };

        return new ResourceConfig().register(ProductResource.class).register(binder);
    }

    @Test
    public void should_return_200_for_get_all_products() throws Exception {
        when(mockProductRepository.getAllProducts()).thenReturn(Arrays.asList(new Product("test",23.00)));

        Response response = target("/products").request().get();

        assertThat(1,is(1));

        assertThat(response.getStatus(),is(200));

        List result = response.readEntity(List.class);

        Map product = (Map) result.get(0);

        assertThat(product.get("name"),is("test"));

        assertThat(product.get("price"),is("23.0"));

        assertThat(((String)(product.get("uri"))).contains("/products/"),is(true));
    }
}
