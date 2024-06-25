package co.com.stockap.catalog.application.usecase.impl;

import java.util.Objects;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.com.stockap.catalog.application.dto.ProductRequest;
import co.com.stockap.catalog.application.exception.ProductAlreadyExistsException;
import co.com.stockap.catalog.application.usecase.AddProduct;
import co.com.stockap.catalog.domain.entity.Product;
import co.com.stockap.catalog.domain.enums.StatusEnum;
import co.com.stockap.catalog.domain.repository.GetProductByNameRepository;
import co.com.stockap.catalog.domain.repository.SaveProductRepository;

@Service
public class AddProductDefault implements AddProduct {

	private SaveProductRepository saveProductRepository;
	private GetProductByNameRepository getProductByNameRepository;

	public AddProductDefault(SaveProductRepository saveProductRepository, 
			GetProductByNameRepository getProductByNameRepository) {
		this.saveProductRepository = saveProductRepository;
		this.getProductByNameRepository = getProductByNameRepository;
	}

	@Override
	public void execute(ProductRequest request) {
		this.validateAlreadyCreated(request);
		Product newProduct = this.buildProduct(request);
		this.saveProductRepository.execute(newProduct);
	}
	
	private void validateAlreadyCreated(ProductRequest request) {
		Product product = this.getProductByNameRepository.execute(request.name());
		if (Objects.nonNull(product)) {
			throw new ProductAlreadyExistsException(String.format("Product %s already exists", request.name()));
		}
	}

	private Product buildProduct(ProductRequest request) {
		return Product.Builder.aProduct()
				.name(request.name())
				.price(request.price())
				.quantity(request.quantity())
				.status(Optional.ofNullable(request.status()).orElse(StatusEnum.ACTIVE.name()))
				.build();
	}

}
