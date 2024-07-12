package co.com.stockap.catalog.infraestructure.database.util;

import static com.mongodb.client.model.Sorts.ascending;
import static com.mongodb.client.model.Sorts.descending;
import static com.mongodb.client.model.Sorts.orderBy;
import static com.mongodb.client.model.Filters.and;
import static com.mongodb.client.model.Filters.eq;
import static com.mongodb.client.model.Filters.empty;
import static com.mongodb.client.model.Updates.combine;
import static com.mongodb.client.model.Updates.set;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

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
import com.mongodb.client.result.UpdateResult;

import co.com.stockap.catalog.infraestructure.database.handler.DocumentHandler;
import jakarta.validation.constraints.NotNull;

public abstract class MQLRepository {
	
	private MongoClientFactoryBean mongoClientFactoryBean;
	private MongoDatabaseFactory mongoDatabaseFactory;
	private final String ORDER_BY_DESC = "DESC";
	
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
	
	protected <T> long runUpdate(String collectionName, Map<String,Object> filterMap, Map<String,Object> updateMap) throws Exception {
		try(MongoClient mongoClient = this.mongoClientFactoryBean.getObject()) {
			
			MongoDatabase database = this.mongoDatabaseFactory.getMongoDatabase();
			MongoCollection<Document> collection = database.getCollection(collectionName);
			
			try {
				UpdateResult result = collection.updateOne(this.createDynamicFilters(filterMap), this.createDynamicUpdates(updateMap));
				return result.getModifiedCount();
            } catch (MongoException me) {
                throw new Exception("Unable to insert due to an error: ", me);
                // TODO Logger
            }
		}
	}
	
	protected <T> T runQuery(String collectionName, DocumentHandler<T> handler, Map<String,Object> filterMap, Map<String,Object> sortMap, Integer limit, Integer offset, List<String> fieldNames) throws Exception {
		try(MongoClient mongoClient = this.mongoClientFactoryBean.getObject()) {
			

			MongoDatabase database = this.mongoDatabaseFactory.getMongoDatabase();
			MongoCollection<Document> collection = database.getCollection(collectionName);
			
			try {
				FindIterable<Document> documents = collection.find()
						.filter(Objects.isNull(filterMap) ? empty() : this.createDynamicFilters(filterMap))
						.sort(Objects.isNull(sortMap) ? null : this.createDynamicSorts(sortMap))
						.limit(Objects.isNull(limit) ? Integer.MAX_VALUE : limit)
						.skip(Objects.isNull(offset) ? 0 : offset)
						.projection(Projections.include(fieldNames));
				return handler.handle(documents.iterator());
			} catch (MongoException me) {
                throw new Exception(String.format("Unable to find %s due to an error: ", handler.getClass().getName()), me);
                // TODO Logger
            }
		}
	}
	
	private Bson createDynamicFilters(Map<String,Object> filterMap) {
		List<Bson> conditions = filterMap.entrySet()
				.stream()
				.map(filter -> eq(filter.getKey(), filter.getValue()))
				.collect(Collectors.toList());
		if (conditions.size() > 1) {
			return and(conditions);
		}
		return conditions.get(0);
	}
	
	private Bson createDynamicSorts(Map<String,Object> sortMap) {
		List<Bson> conditions = sortMap.entrySet()
				.stream()
				.map(sort -> ORDER_BY_DESC.equalsIgnoreCase((String)sort.getValue()) ? descending(sort.getKey()) : ascending(sort.getKey()))
				.collect(Collectors.toList());
		return orderBy(conditions);
	}
	
	private Bson createDynamicUpdates(Map<String,Object> updateMap) {
		List<Bson> conditions = updateMap.entrySet()
				.stream()
				.map(update -> set(update.getKey(), update.getValue()))
				.collect(Collectors.toList());
		if (conditions.size() > 1) {
			return combine(conditions);
		}
		return conditions.get(0);
	}

}
