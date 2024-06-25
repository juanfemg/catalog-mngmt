package co.com.stockap.catalog.infraestructure.database.util;

import java.util.List;
import org.bson.Document;
import org.bson.conversions.Bson;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import com.mongodb.MongoException;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import com.mongodb.client.model.Projections;

import co.com.stockap.catalog.infraestructure.database.handler.DocumentHandler;
import jakarta.validation.constraints.NotNull;

public abstract class MQLRepository {
	
	private MongoClientFactoryBean mongoClientFactoryBean;
	private MongoDatabaseFactory mongoDatabaseFactory;
	
	protected MQLRepository(@NotNull MongoClientFactoryBean mongoClientFactoryBean, @NotNull MongoDatabaseFactory mongoDatabaseFactory) {
		this.mongoClientFactoryBean = mongoClientFactoryBean;
		this.mongoDatabaseFactory = mongoDatabaseFactory;
	}
	
	protected <T> void runInsert(String collectionName, Document document) throws Exception {
		try(MongoClient mongoClient = this.mongoClientFactoryBean.getObject()) {
			
			MongoDatabase database = this.mongoDatabaseFactory.getMongoDatabase();
			MongoCollection<Document> collection = database.getCollection(collectionName);
			
			try {
				collection.insertOne(document);
            } catch (MongoException me) {
                throw new Exception("Unable to insert due to an error: ", me);
                // TODO Logger
            }
		}
	}
	
	protected <T> T runQuery(String collectionName, DocumentHandler<T> handler, Bson filter, List<String> fieldNames) throws Exception {
		try(MongoClient mongoClient = this.mongoClientFactoryBean.getObject()) {
			
			MongoDatabase database = this.mongoDatabaseFactory.getMongoDatabase();
			MongoCollection<Document> collection = database.getCollection(collectionName);
			
			try {
				FindIterable<Document> documents = collection.find(filter)
						.projection(Projections.include(fieldNames));
				return handler.handle(documents.iterator());
			} catch (MongoException me) {
                throw new Exception(String.format("Unable to find %s due to an error: ", handler.getClass().getName()), me);
                // TODO Logger
            }
		}
	}
	

}
