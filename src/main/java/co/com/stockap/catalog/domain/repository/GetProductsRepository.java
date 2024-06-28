package co.com.stockap.catalog.domain.repository;

import java.util.List;
import java.util.Map;

import co.com.stockap.catalog.domain.entity.Product;

public interface GetProductsRepository {
	
	List<Product> execute(Map<String,Object> filterMap, Map<String,Object> sortMap, Integer limit, Integer offset);
	
}
