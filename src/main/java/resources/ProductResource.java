package resources;

import domain.Product;
import json.ProductJson;
import repository.ProductRepository;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;
import java.util.List;
import java.util.stream.Collectors;

@Path("/products")
public class ProductResource {

    @Inject
    ProductRepository productRepository;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<ProductJson> getAllProducts(@Context UriInfo uriInfo){
        List<Product> allProducts = productRepository.getAllProducts();
        List<ProductJson> productJsons = allProducts.stream().map(product -> new ProductJson(product,uriInfo)).collect(Collectors.toList());
        return productJsons;
    }
}
