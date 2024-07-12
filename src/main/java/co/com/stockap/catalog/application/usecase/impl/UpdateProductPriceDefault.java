package co.com.stockap.catalog.application.usecase.impl;

import org.springframework.stereotype.Service;

import co.com.stockap.catalog.application.dto.ProductPriceRequest;
import co.com.stockap.catalog.application.exception.BusinessException;
import co.com.stockap.catalog.application.usecase.UpdateProductPrice;
import co.com.stockap.catalog.domain.repository.UpdateProductPriceRepository;

@Service
public class UpdateProductPriceDefault implements UpdateProductPrice {
	
private UpdateProductPriceRepository updateProductPriceRepository;
	
	public UpdateProductPriceDefault(UpdateProductPriceRepository updateProductPriceRepository) {
		this.updateProductPriceRepository = updateProductPriceRepository;
	}

	@Override
	public long execute(String id, ProductPriceRequest request) {
		this.validateCorrectPrice(request);
		return this.updateProductPriceRepository.execute(id, request.price());
	}
	
	private void validateCorrectPrice(ProductPriceRequest request) {
		if (request.price() < 0) {
			throw new BusinessException(String.format("Failed becasuse of %d is not correct. It should be a positive number or zero", request.price()));
		}
	}

}
