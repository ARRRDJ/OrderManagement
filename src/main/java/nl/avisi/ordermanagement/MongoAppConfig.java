package nl.avisi.ordermanagement;

import com.mongodb.Mongo;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.config.AbstractMongoConfiguration;

/**
 * Created by Sam on 27-5-2016.
 */
@Configuration
public class MongoAppConfig extends AbstractMongoConfiguration {
    @Override
    protected String getDatabaseName() {
        return "OrderManagement";
    }

    @Bean
    public Mongo mongo() throws Exception {
        return new Mongo("localhost");
    }

    public String getMappingBasePackage() {
        return "nl.avisi.ordermanagement.domain";
    }
}
