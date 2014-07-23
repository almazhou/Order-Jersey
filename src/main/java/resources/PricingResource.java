package resources;

import domain.Pricing;
import domain.Product;
import json.PricingJson;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

public class PricingResource {
    private final Product product;
    private final UriInfo uriInfo;

    public PricingResource(Product product, UriInfo uriInfo) {

        this.product = product;
        this.uriInfo = uriInfo;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<PricingJson> getAllPricings(){
        List<Pricing> pricings = product.getPricings();

        return pricings.stream().map(pricing -> new PricingJson(product,pricing,uriInfo)).collect(Collectors.toList());
    }

    @GET
    @Path("/{priceId}")
    @Produces(MediaType.APPLICATION_JSON)
    public PricingJson getPricingById(@PathParam("priceId") int priceId){
        Pricing pricing = product.getPriceById(priceId);

        return new PricingJson(product,pricing,uriInfo);
    }
}
