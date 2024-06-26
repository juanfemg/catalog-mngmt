package co.com.stockap.catalog.infraestructure.database;


import java.util.Arrays;
import java.util.List;
import java.util.Map;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.stereotype.Repository;

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
			Map<String,Object> filterMap = Map.of("name", name);
			return super.runQuery(Collection.PRODUCT.getProduct(), ProductHandler::handle, filterMap, null, 1, 0, PROJECTION_FIELDS);
		} catch (Exception e) {
			throw new RepositoryException(String.format("Failed to get document with name %s", name), e);
		}
	}

}
