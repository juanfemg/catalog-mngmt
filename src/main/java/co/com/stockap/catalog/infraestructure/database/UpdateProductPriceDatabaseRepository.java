package co.com.stockap.catalog.infraestructure.database;

import java.util.Map;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.stereotype.Repository;

import co.com.stockap.catalog.application.exception.RepositoryException;
import co.com.stockap.catalog.domain.repository.UpdateProductPriceRepository;
import co.com.stockap.catalog.infraestructure.database.enums.Collection;
import co.com.stockap.catalog.infraestructure.database.util.MQLRepository;
import jakarta.validation.constraints.NotNull;

@Repository
public class UpdateProductPriceDatabaseRepository extends MQLRepository implements UpdateProductPriceRepository {

	protected UpdateProductPriceDatabaseRepository(@NotNull MongoClientFactoryBean mongoClientFactoryBean,
			@NotNull MongoDatabaseFactory mongoDatabaseFactory) {
		super(mongoClientFactoryBean, mongoDatabaseFactory);
	}

	@Override
	public long execute(String id, double price) {
		try {
			Map<String,Object> filterMap = Map.of("_id", new ObjectId(id));
			Map<String,Object> updateMap = Map.of("price", price);
			return super.runUpdate(Collection.PRODUCT.getProduct(), filterMap, updateMap);
		} catch (Exception e) {
			throw new RepositoryException(String.format("Failed to update document with id %s and price %s", id, price), e);
		}
	}

}
