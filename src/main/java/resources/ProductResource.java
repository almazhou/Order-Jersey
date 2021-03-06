package resources;

import domain.Pricing;
import domain.Product;
import json.ProductJson;
import repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.*;
import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public List<ProductJson> getAllProducts(@Context UriInfo uriInfo) {
        List<Product> allProducts = productRepository.getAllProducts();
        List<ProductJson> productJsons = allProducts.stream().map(product -> new ProductJson(product, uriInfo)).collect(Collectors.toList());
        return productJsons;
    }

    @POST
    @Consumes(MediaType.APPLICATION_FORM_URLENCODED)
    public Response saveProduct(@Context UriInfo uriInfo, Form form) {
        String name = form.asMap().getFirst("name");
        String priceValue = form.asMap().getFirst("price");
        Pricing pricing = new Pricing(Double.valueOf(priceValue).doubleValue());
        Product productCreated = new Product(name,pricing);
        productRepository.save(productCreated);

        return Response.created(URI.create(uriInfo.getBaseUri()+"/products/"+productCreated.getId())).build();
    }


    @GET
    @Path("/{id}")
    @Produces({MediaType.APPLICATION_JSON,MediaType.APPLICATION_XML})
    public ProductJson getProductById(@PathParam("id") String id, @Context UriInfo uriInfo) {

        Product productById = productRepository.getProductById(id);

        return new ProductJson(productById, uriInfo);
    }

    @Path("/{id}/pricings")
    public PricingResource getPricingResource(@PathParam("id") String id, @Context UriInfo uriInfo) {
        Product productById = productRepository.getProductById(id);

        return new PricingResource(productById,uriInfo);
    }
}
