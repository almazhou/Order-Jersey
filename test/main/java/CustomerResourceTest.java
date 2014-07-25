import exception.RecordNotFoundException;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.CustomerRepository;
import resources.Customer;
import resources.CustomerResource;

import javax.ws.rs.core.Application;
import javax.ws.rs.core.Response;
import java.util.Arrays;

import static org.hamcrest.core.Is.is;
import static org.junit.Assert.assertThat;
import static org.mockito.Mockito.when;

@RunWith(MockitoJUnitRunner.class)
public class CustomerResourceTest extends JerseyTest {
    @Mock
    CustomerRepository mockCustomerRepository;

    @Override
    protected Application configure() {
        AbstractBinder abstractBinder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockCustomerRepository).to(CustomerRepository.class);
            }
        };
        return new ResourceConfig().register(CustomerResource.class).register(RecordNotFoundException.class).register(abstractBinder);
    }

    @Test
    public void should_return_200_for_get_all_customers() throws Exception {
        Customer customer = new Customer("customer","address");
        when(mockCustomerRepository.getAllCustomers()).thenReturn(Arrays.asList(customer));
        Response response = target("/customers").request().get();

        assertThat(response.getStatus(),is(200));


    }
}
