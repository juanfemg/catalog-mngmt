package co.com.stockap.catalog.application.entrypoint.impl;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.com.stockap.catalog.application.dto.ProductRequest;
import co.com.stockap.catalog.application.entrypoint.Entrypoint;
import co.com.stockap.catalog.application.usecase.AddProduct;

@RestController
@RequestMapping("/products")
public class AddProductEntrypoint extends Entrypoint {
	
	private AddProduct addProduct;
	private static final String RESPONSE_MESSAGE = "Product has been created succesfully";
	
	public AddProductEntrypoint(AddProduct addProduct) {
		this.addProduct = addProduct;
	}

	@PostMapping(consumes = "application/json")
	public ResponseEntity<String> handle(@RequestBody ProductRequest request) {
		this.addProduct.execute(request);
		return this.buildResponse(HttpStatus.CREATED, RESPONSE_MESSAGE);
	}

}
