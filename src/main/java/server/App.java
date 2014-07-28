package server;

import database.MongoDb;
import domain.Pricing;
import domain.Product;
import org.glassfish.grizzly.http.server.HttpServer;
import org.glassfish.hk2.utilities.binding.AbstractBinder;
import org.glassfish.jersey.grizzly2.httpserver.GrizzlyHttpServerFactory;
import org.glassfish.jersey.moxy.json.MoxyJsonConfig;
import org.glassfish.jersey.server.ResourceConfig;
import org.mongodb.morphia.Datastore;
import repository.ProductMapper;
import repository.ProductRepository;

import javax.ws.rs.ext.ContextResolver;
import java.io.IOException;
import java.net.URI;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

public class App {
    private static final URI BASE_URI = URI.create("http://localhost:9998/");
    public static void main(String[] args) {
        try {
            System.out.println("JSON with MOXy Jersey Example App");

            final HttpServer server = GrizzlyHttpServerFactory.createHttpServer(BASE_URI, createApp());

            System.out.println(String.format("Application started.%nHit enter to stop it..."));
            System.in.read();
            server.shutdownNow();
        } catch (IOException ex) {
            Logger.getLogger(App.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public static ResourceConfig createApp() {
        Datastore datastore = MongoDb.createDataStore("production");

        final ProductRepository morphiaProductsRepository = new ProductMapper(datastore);
        morphiaProductsRepository.save(new Product("zhouxuan",new Pricing(45.0)));
        AbstractBinder abstractBinder = new AbstractBinder() {
            @Override
            protected void configure() {
                bind(morphiaProductsRepository).to(ProductRepository.class);
            }
        };

        return new ResourceConfig().
                packages("resources").
                register(abstractBinder)
                .register(createMoxyJsonResolver());
    }
    public static ContextResolver<MoxyJsonConfig> createMoxyJsonResolver() {
        final MoxyJsonConfig moxyJsonConfig = new MoxyJsonConfig();
        HashMap<String, String> namespacePrefixMapper = new HashMap<String, String>(1);
        namespacePrefixMapper.put("http://www.w3.org/2001/XMLSchema-instance", "xsi");
        moxyJsonConfig.setNamespacePrefixMapper(namespacePrefixMapper).setNamespaceSeparator(':');
        return moxyJsonConfig.resolver();
    }
}
