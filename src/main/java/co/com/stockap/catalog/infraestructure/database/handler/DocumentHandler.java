package co.com.stockap.catalog.infraestructure.database.handler;

import org.bson.Document;

import com.mongodb.client.MongoCursor;

public interface DocumentHandler<T> {

	T handle(MongoCursor<Document> cursor);
}
