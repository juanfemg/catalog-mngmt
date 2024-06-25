package co.com.stockap.catalog.infraestructure.database.handler;

import org.bson.Document;
import com.mongodb.client.MongoCursor;

import co.com.stockap.catalog.domain.entity.Product;

public class ProductHandler {

	public static Product handler(MongoCursor<Document> cursor) {
		if (cursor.hasNext()) {
			return buildEntity(cursor.next());
		}
		return null;
	}
	
	private static Product buildEntity(Document document) {
		return Product.Builder.aProduct()
				.name(document.getString("name"))
				.price(document.getDouble("price"))
				.quantity(document.getInteger("quantity"))
				.status(document.getString("status"))
				.build();
	}
}
