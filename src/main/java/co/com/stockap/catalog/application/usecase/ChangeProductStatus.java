package co.com.stockap.catalog.application.usecase;

import co.com.stockap.catalog.application.dto.ProductStatusRequest;

public interface ChangeProductStatus {
	
	long execute(String id, ProductStatusRequest request);

}
