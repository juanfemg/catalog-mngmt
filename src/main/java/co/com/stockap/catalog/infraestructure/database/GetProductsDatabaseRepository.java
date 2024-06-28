package co.com.stockap.catalog.infraestructure.database;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;

import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.stereotype.Repository;

import co.com.stockap.catalog.application.exception.RepositoryException;
import co.com.stockap.catalog.domain.entity.Product;
import co.com.stockap.catalog.domain.repository.GetProductsRepository;
import co.com.stockap.catalog.infraestructure.database.enums.Collection;
import co.com.stockap.catalog.infraestructure.database.handler.ProductHandler;
import co.com.stockap.catalog.infraestructure.database.util.MQLRepository;
import jakarta.validation.constraints.NotNull;

@Repository
public class GetProductsDatabaseRepository extends MQLRepository implements GetProductsRepository {

	private static List<String> PROJECTION_FIELDS = Arrays.asList("name", "category", "price", "quantity", "status");
	
	protected GetProductsDatabaseRepository(@NotNull MongoClientFactoryBean mongoClientFactoryBean,
			@NotNull MongoDatabaseFactory mongoDatabaseFactory) {
		super(mongoClientFactoryBean, mongoDatabaseFactory);
	}

	@Override
	public List<Product> execute(Map<String, Object> filterMap, Map<String,Object> sortMap, Integer limit, Integer offset) {
		try {
			return super.runQuery(Collection.PRODUCT.getProduct(), ProductHandler::handleList, filterMap, sortMap, limit, offset, PROJECTION_FIELDS);
		} catch (Exception e) {
			throw new RepositoryException(String.format("Failed to get documents%s", (Objects.nonNull(filterMap) ? String.format(" with filters %s", filterMap) : "")), e);
		}
	}

}
