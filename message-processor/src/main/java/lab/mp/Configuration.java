package lab.mp;

import com.mongodb.MongoClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ImportResource;
import org.springframework.data.mongodb.core.MongoTemplate;

import java.net.UnknownHostException;

/**
 * Application Spring configuration.
 */
@org.springframework.context.annotation.Configuration
@ImportResource("classpath:spring-config.xml")
public class Configuration {

    public @Bean
    MongoClient mongo() throws UnknownHostException {
        return new MongoClient("localhost");
    }

    public @Bean
    MongoTemplate mongoTemplate() throws Exception {
        return new MongoTemplate(mongo(), "mtp");
    }
}
