package co.com.stockap.catalog.application.entrypoint;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public abstract class Entrypoint {
	
	protected <T> ResponseEntity<T> buildResponse(HttpStatus httpStatus, T body) {
		return ResponseEntity.status(httpStatus).body(body);
	}
	
}
