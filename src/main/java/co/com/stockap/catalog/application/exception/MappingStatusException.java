package co.com.stockap.catalog.application.exception;

public class MappingStatusException extends RuntimeException {

	public MappingStatusException(String message) {
		super(message);
	}
	
	public MappingStatusException(String message, Exception cause) {
		super(message, cause);
	}

}
