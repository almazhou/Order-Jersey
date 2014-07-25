package resources;

import json.CustomerJson;
import repository.CustomerRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/customers")
public class CustomerResource {
    @Inject
    CustomerRepository customerRepository;


    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<CustomerJson> getAllCustomers(@Context UriInfo uriInfo){
        List<Customer> allCustomers = customerRepository.getAllCustomers();

        List<CustomerJson> customerJsons = allCustomers.stream().map(customer -> new CustomerJson(customer, uriInfo)).collect(Collectors.toList());

        return customerJsons;
    }
}
