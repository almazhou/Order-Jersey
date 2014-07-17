package json;

import domain.Product;

import javax.ws.rs.core.UriInfo;

public class ProductJson {
    private final Product product;
    private final UriInfo uriInfo;

    public ProductJson(Product product, UriInfo uriInfo) {

        this.product = product;
        this.uriInfo = uriInfo;
    }

    public String getUri(){
        return uriInfo.getBaseUri()+"products/"+product.getId();
    }

    public String getName(){
        return product.getName();
    }

    public String getPrice(){
        return String.valueOf(product.getPrice());
    }

    public String getProductId(){
        return String.valueOf(product.getProductId());
    }
}
