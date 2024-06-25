package co.com.stockap.catalog.application.exception;

public class ProductAlreadyExistsException extends RuntimeException {

	public ProductAlreadyExistsException(String message) {
		super(message);
	}
	
	public ProductAlreadyExistsException(String message, Exception cause) {
		super(message, cause);
	}
	
}
