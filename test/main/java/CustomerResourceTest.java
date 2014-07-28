import exception.RecordNotFoundException;
import exception.RecordNotFoundExceptionHandler;
import org.bson.types.ObjectId;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.test.JerseyTest;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mock;
import org.mockito.runners.MockitoJUnitRunner;
import repository.CustomerRepository;
import resources.Customer;
import resources.CustomerResource;

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
public class CustomerResourceTest extends JerseyTest {
    @Mock
    CustomerRepository mockCustomerRepository;

    @Captor
    ArgumentCaptor<Customer> customerArgumentCaptor;

    private String CUSTOMER_ID = "1234567890efabcd34567892";


    @Override
    protected Application configure() {
        AbstractBinder abstractBinder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(mockCustomerRepository).to(CustomerRepository.class);
            }
        };
        return new ResourceConfig().register(CustomerResource.class).register(RecordNotFoundExceptionHandler.class).register(abstractBinder);
    }

    @Test
    public void should_return_200_for_get_all_customers() throws Exception {
        Customer customer = new Customer(new ObjectId(CUSTOMER_ID),"customer","address");
        when(mockCustomerRepository.getAllCustomers()).thenReturn(Arrays.asList(customer));

        Response response = target("/customers").request().get();

        assertThat(response.getStatus(),is(200));

        List list = response.readEntity(List.class);

        Map gotCustomer = (Map) list.get(0);

        assertThat(gotCustomer.get("name"),is("customer"));
        assertThat(gotCustomer.get("address"),is("address"));
        String uri = (String) gotCustomer.get("uri");
        assertThat(uri.contains("/customers/"+customer.getId()),is(true));

    }

    @Test
    public void should_return_200_for_get_one_customers() throws Exception {
        Customer customer = new Customer(new ObjectId(CUSTOMER_ID),"customer","address");
        when(mockCustomerRepository.getCustomerWithId(CUSTOMER_ID)).thenReturn(customer);

        Response response = target("/customers/" + CUSTOMER_ID).request().get();

        assertThat(response.getStatus(),is(200));

        Map gotCustomer = response.readEntity(Map.class);

        assertThat(gotCustomer.get("name"),is("customer"));
        assertThat(gotCustomer.get("address"),is("address"));
        String uri = (String) gotCustomer.get("uri");
        assertThat(uri.contains("/customers/"+customer.getId()),is(true));

    }

    @Test
    public void should_return_404_for_get_customer_not_exist() throws Exception {
        when(mockCustomerRepository.getCustomerWithId(CUSTOMER_ID)).thenThrow(RecordNotFoundException.class);

        Response response = target("/customers/" + CUSTOMER_ID).request().get();

        assertThat(response.getStatus(),is(404));

    }

    @Test
    public void should_return_201_for_post_one_customer() throws Exception {

        Response response = target("/customers").request().post(Entity.form(((new Form().param("name", "customer").param("address", "streetone")))));

        verify(mockCustomerRepository).save(customerArgumentCaptor.capture());

        assertThat(response.getStatus(),is(201));

        assertThat(customerArgumentCaptor.getValue().getName(),is("customer"));
        assertThat(customerArgumentCaptor.getValue().getAddress(),is("streetone"));
    }

}
