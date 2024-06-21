package co.com.stockap.catalog.domain.repository;

import co.com.stockap.catalog.domain.entity.Product;

public interface SaveProductRepository {	
	void execute(Product product);

}
