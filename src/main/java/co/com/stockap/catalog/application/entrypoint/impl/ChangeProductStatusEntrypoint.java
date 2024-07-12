package co.com.stockap.catalog.application.entrypoint.impl;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import co.com.stockap.catalog.application.dto.ProductStatusRequest;
import co.com.stockap.catalog.application.entrypoint.Entrypoint;
import co.com.stockap.catalog.application.usecase.ChangeProductStatus;

@RestController
@RequestMapping("/products")
public class ChangeProductStatusEntrypoint extends Entrypoint {

	private ChangeProductStatus changeProductStatus;
	private static final String RESPONSE_MESSAGE = "Product status has been updated succesfully";
	private static final String RESPONSE_NOT_FOUND_MESSAGE = "Product does not exist";
	
	public ChangeProductStatusEntrypoint(ChangeProductStatus changeProductStatus) {
		this.changeProductStatus = changeProductStatus;
	}
	
	@PostMapping("/{id}/status")
	public ResponseEntity<Object> handle(@RequestBody ProductStatusRequest request, @PathVariable(name = "id") String id) {
		long response = this.changeProductStatus.execute(id, request);
		if (response == 0) {
			return this.buildResponse(HttpStatus.NOT_FOUND, RESPONSE_NOT_FOUND_MESSAGE);
		}
		return this.buildResponse(HttpStatus.OK, RESPONSE_MESSAGE);
	}
}
