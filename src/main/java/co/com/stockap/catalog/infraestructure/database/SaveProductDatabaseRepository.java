package co.com.stockap.catalog.infraestructure.database;

import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.core.MongoClientFactoryBean;
import org.springframework.stereotype.Repository;

import co.com.stockap.catalog.application.exception.RepositoryException;
import co.com.stockap.catalog.domain.entity.Product;
import co.com.stockap.catalog.domain.repository.SaveProductRepository;
import co.com.stockap.catalog.infraestructure.database.enums.Collection;
import co.com.stockap.catalog.infraestructure.database.util.MQLRepository;
import jakarta.validation.constraints.NotNull;

@Repository
public class SaveProductDatabaseRepository extends MQLRepository implements SaveProductRepository {

	public SaveProductDatabaseRepository(@NotNull MongoClientFactoryBean mongoClientFactoryBean,
			@NotNull MongoDatabaseFactory mongoDatabaseFactory) {
		super(mongoClientFactoryBean, mongoDatabaseFactory);
	}

	@Override
	public void execute(Product product) {
		try {
			Document document = new Document()
					.append("_id", new ObjectId())
					.append("name", product.getName())
					.append("price", product.getPrice())
					.append("quantity", product.getQuantity())
					.append("status", product.getStatus());
			super.runInsert(Collection.PRODUCT.getProduct(), document);
		} catch (Exception e) {
			// TODO Logger
			throw new RepositoryException(String.format("Failed to save document with name %s", product.getName()), e);
		}
	}

}
