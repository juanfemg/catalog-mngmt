package co.com.stockap.catalog.domain.repository;

import co.com.stockap.catalog.domain.entity.Product;

public interface GetProductByNameRepository {
	
	Product execute(String name);
	
}
