package json;

import resources.Customer;

import javax.ws.rs.core.UriInfo;
import java.net.URI;

public class CustomerJson {
    private final Customer customer;
    private final UriInfo uriInfo;

    public CustomerJson(Customer customer, UriInfo uriInfo) {
        this.customer = customer;
        this.uriInfo = uriInfo;
    }

    public String getName(){
        return customer.getName();
    }

    public String getAddress(){
        return customer.getAddress();
    }

    public String getUri(){
        URI baseUri = uriInfo.getBaseUri();
        String customerUri = "customers/"+customer.getId().toString();
        return baseUri + customerUri;
    }
}
