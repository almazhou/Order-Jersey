package json;

import domain.Pricing;
import domain.Product;

import javax.ws.rs.core.UriInfo;

public class PricingJson {
    private final Product product;
    private final UriInfo uriInfo;

    public PricingJson(Product product, UriInfo uriInfo) {
        this.product = product;
        this.uriInfo = uriInfo;
    }

    public Pricing getPricing(){
        return product.getPrice();
    }

    public String getUri() {
        return uriInfo.getBaseUri() + "products/" + getProductId() + "/pricings/" + getPricingId();
    }

    private String getPricingId() {
        Pricing pricing = getPricing();
        if(pricing == null || pricing.getId() == null) return "";
        return pricing.getId().toString();
    }

    public String getProductId() {
        return product.getId().toString();
    }

    public String getPrice() {
        return String.valueOf(getPricing().getAmount());
    }

    public String getDate() {
        return String.valueOf(getPricing().getModifiedTime());
    }
}
