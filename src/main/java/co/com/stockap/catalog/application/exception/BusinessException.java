package co.com.stockap.catalog.application.exception;

public class BusinessException extends RuntimeException {

	public BusinessException(String message) {
		super(message);
	}
	
	public BusinessException(String message, Exception cause) {
		super(message, cause);
	}

}
