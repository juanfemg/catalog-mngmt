package co.com.stockap.catalog.application.exception;

public class RepositoryException extends RuntimeException {

	public RepositoryException(String message) {
		super(message);
	}
	
	public RepositoryException(String message, Exception cause) {
		super(message, cause);
	}

}
