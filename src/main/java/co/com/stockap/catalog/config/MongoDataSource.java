package co.com.stockap.catalog.config;

import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.client.MongoClient;

import static org.bson.codecs.configuration.CodecRegistries.fromProviders;
import static org.bson.codecs.configuration.CodecRegistries.fromRegistries;

@Configuration
public class MongoDataSource {

	@Bean
	MongoClientFactoryBean mongo(@Value("${spring.data.mongodb.uri}") String uri) throws Exception {
		MongoClientFactoryBean mongo = new MongoClientFactoryBean();
		ConnectionString conn = new ConnectionString(uri);
		CodecRegistry pojoCodecRegistry = fromProviders(
				PojoCodecProvider.builder()
				.automatic(true)
				.build());
		CodecRegistry codecRegistry = fromRegistries(MongoClientSettings.getDefaultCodecRegistry(), pojoCodecRegistry);
		MongoClientSettings clientSettings = MongoClientSettings.builder()
                .codecRegistry(codecRegistry)
                .build();
		mongo.setMongoClientSettings(clientSettings);
		mongo.setConnectionString(conn);
		return mongo;
	}
	
	@Bean
	MongoDatabaseFactory database(@Value("${spring.data.mongodb.database}") String database, MongoClient mongo) {
		return new SimpleMongoClientDatabaseFactory(mongo, database);
	}

}
