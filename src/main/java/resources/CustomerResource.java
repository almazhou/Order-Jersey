package resources;

import json.CustomerJson;
import repository.CustomerRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomerResource {
    @Inject
    CustomerRepository customerRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerJson> getAllCustomers(@Context UriInfo uriInfo) {
        List<Customer> allCustomers = customerRepository.getAllCustomers();

        List<CustomerJson> customerJsons = allCustomers.stream().map(customer -> new CustomerJson(customer, uriInfo)).collect(Collectors.toList());

        return customerJsons;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response saveCustomer(@Context UriInfo uriInfo, Form form) {
        String name = form.asMap().getFirst("name");
        String address = form.asMap().getFirst("address");
        Customer customer = new Customer(address, name);

        customerRepository.save(customer);

        return Response.created(URI.create(uriInfo.getBaseUri()+"customers/"+ customer.getId())).build();
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public CustomerJson getCustomerWithId(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Customer customerWithId = customerRepository.getCustomerWithId(id);

        return new CustomerJson(customerWithId, uriInfo);
    }
}
