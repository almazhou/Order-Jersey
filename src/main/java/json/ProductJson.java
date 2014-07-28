package json;

import domain.Product;

import javax.ws.rs.core.UriInfo;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
public class ProductJson {
    private Product product;
    private UriInfo uriInfo;

    public ProductJson() {
    }

    public ProductJson(Product product, UriInfo uriInfo) {

        this.product = product;
        this.uriInfo = uriInfo;
    }

    @XmlElement
    public String getUri(){
        return uriInfo.getBaseUri()+"products/"+product.getId().toString();
    }

    @XmlElement
    public String getName(){
        return product.getName();
    }

    @XmlElement
    public String getPrice(){
        return String.valueOf(product.getPrice().getAmount());
    }

}
