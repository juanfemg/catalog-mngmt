package co.com.stockap.catalog.application.usecase;

import co.com.stockap.catalog.application.dto.ProductRequest;

public interface AddProduct {
	void execute(ProductRequest request);
}
