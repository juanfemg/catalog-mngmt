package co.com.stockap.catalog.application.usecase.impl;

import java.util.Optional;

import org.springframework.stereotype.Service;

import co.com.stockap.catalog.application.dto.ProductRequest;
import co.com.stockap.catalog.application.usecase.AddProduct;
import co.com.stockap.catalog.domain.entity.Product;
import co.com.stockap.catalog.domain.enums.StatusEnum;
import co.com.stockap.catalog.domain.repository.SaveProductRepository;

@Service
public class AddProductDefault implements AddProduct {

	private SaveProductRepository saveProductRepository;

	public AddProductDefault(SaveProductRepository saveProductRepository) {
		this.saveProductRepository = saveProductRepository;
	}

	@Override
	public void execute(ProductRequest request) {
		Product newProduct = this.buildProduct(request);
		this.saveProductRepository.execute(newProduct);
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
