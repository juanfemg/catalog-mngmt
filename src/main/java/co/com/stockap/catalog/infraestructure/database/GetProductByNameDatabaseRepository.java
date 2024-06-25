package co.com.stockap.catalog.infraestructure.database;


import java.util.Arrays;
import java.util.List;

import org.bson.conversions.Bson;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.stereotype.Repository;

import com.mongodb.client.model.Filters;

import co.com.stockap.catalog.application.exception.RepositoryException;
import co.com.stockap.catalog.domain.entity.Product;
import co.com.stockap.catalog.domain.repository.GetProductByNameRepository;
import co.com.stockap.catalog.infraestructure.database.enums.Collection;
import co.com.stockap.catalog.infraestructure.database.handler.ProductHandler;
import co.com.stockap.catalog.infraestructure.database.util.MQLRepository;
import jakarta.validation.constraints.NotNull;

@Repository
public class GetProductByNameDatabaseRepository extends MQLRepository implements GetProductByNameRepository {

	private static List<String> PROJECTION_FIELDS = Arrays.asList("name", "price", "quantity", "status");
	
	public GetProductByNameDatabaseRepository(@NotNull MongoClientFactoryBean mongoClientFactoryBean,
			@NotNull MongoDatabaseFactory mongoDatabaseFactory) {
		super(mongoClientFactoryBean, mongoDatabaseFactory);
	}

	@Override
	public Product execute(String name) {
		try {
			Bson filter = Filters.eq("name", name);
			return super.runQuery(Collection.PRODUCT.getProduct(), ProductHandler::handler, filter, PROJECTION_FIELDS);
		} catch (Exception e) {
			throw new RepositoryException(String.format("Failed to get document with name %s", name), e);
		}
	}

}
