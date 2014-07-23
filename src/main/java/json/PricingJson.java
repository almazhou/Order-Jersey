package json;

import domain.Pricing;
import domain.Product;

import javax.ws.rs.core.UriInfo;

public class PricingJson {
    private final Product product;
    private final Pricing pricing;
    private final UriInfo uriInfo;

    public PricingJson(Product product, Pricing pricing, UriInfo uriInfo) {

        this.product = product;
        this.pricing = pricing;
        this.uriInfo = uriInfo;
    }

    public String getUri(){
        return "/products/"+product.getId()+"/pricings/"+pricing.getId();
    }

    public String getProductId(){
        return String.valueOf(product.getId());
    }

    public String getPrice(){
        return String.valueOf(pricing.getAmount());
    }

    public String getDate(){
        return String.valueOf(pricing.getModifiedTime());
    }
}
