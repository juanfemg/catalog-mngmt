package co.com.stockap.catalog.application.exception;

public class MissingQueryParamException extends RuntimeException {

	public MissingQueryParamException(String message) {
		super(message);
	}
	
	public MissingQueryParamException(String message, Exception cause) {
		super(message, cause);
	}
	
}