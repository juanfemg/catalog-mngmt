package co.com.stockap.catalog.application.usecase;

import co.com.stockap.catalog.application.dto.ProductPriceRequest;

public interface UpdateProductPrice {
	
	long execute(String id, ProductPriceRequest request);

}
