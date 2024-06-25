package co.com.stockap.catalog.application.exception.handler;

import java.util.Optional;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import co.com.stockap.catalog.application.exception.ProductAlreadyExistsException;
import co.com.stockap.catalog.application.exception.RepositoryException;

@RestControllerAdvice
public class AppExceptionHandler extends ResponseEntityExceptionHandler {
	
	@Override
	protected ResponseEntity<Object> handleHttpMessageNotReadable(HttpMessageNotReadableException ex, HttpHeaders headers, HttpStatusCode status, WebRequest request) {
		String message = "Failed to read request";
		ErrorResponse body = createBodyResponse(HttpStatus.BAD_REQUEST, ex, message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(ProductAlreadyExistsException.class)
	protected ResponseEntity<Object> handleProductAlreadyExists(ProductAlreadyExistsException ex) {
		String message = "Product already exists";
		ErrorResponse body = createBodyResponse(HttpStatus.BAD_REQUEST, ex, message);
		return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(body);
	}
	
	@ExceptionHandler(RepositoryException.class)
	protected ResponseEntity<Object> handleRepository(RepositoryException ex) {
		ErrorResponse body = createBodyResponse(HttpStatus.INTERNAL_SERVER_ERROR, ex, null);
		return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(body);
	}
	
	private ErrorResponse createBodyResponse(HttpStatus status, Exception ex, String message) {
		String detail = Optional.ofNullable(message).orElse(ex.getMessage());
		return new ErrorResponse(status.getReasonPhrase(), status.value(), detail);
	}

	record ErrorResponse(String response, int status, String detail) {}
}
