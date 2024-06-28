package co.com.stockap.catalog.infraestructure.database.handler;

import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import com.mongodb.client.MongoCursor;

import co.com.stockap.catalog.domain.entity.Product;

public class ProductHandler {

	public static Product handle(MongoCursor<Document> cursor) {
		if (cursor.hasNext()) {
			return buildEntity(cursor.next());
		}
		return null;
	}
	
	public static List<Product> handleList(MongoCursor<Document> cursor) {
		List<Product> entities = new ArrayList<>();
		while (cursor.hasNext()) {
			entities.add(buildEntity(cursor.next()));
		}
		return entities;
	}
	
	private static Product buildEntity(Document document) {
		return Product.Builder.aProduct()
				.name(document.getString("name"))
				.category(document.getString("category"))
				.price(document.getDouble("price"))
				.quantity(document.getInteger("quantity"))
				.status(document.getString("status"))
				.build();
	}
}
