package co.com.stockap.catalog.application.usecase.impl;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import co.com.stockap.catalog.application.dto.ProductResponse;
import co.com.stockap.catalog.application.usecase.GetProducts;
import co.com.stockap.catalog.domain.entity.Product;
import co.com.stockap.catalog.domain.repository.GetProductsRepository;

@Service
public class GetProductsDefault implements GetProducts {

	private GetProductsRepository getProductsRepository;
	
	public GetProductsDefault(GetProductsRepository getProductsRepository) {
		this.getProductsRepository = getProductsRepository;
	}
	
	@Override
	public List<ProductResponse> execute(Map<String,Object> filter, Map<String,Object> sort, Integer limit, Integer offset) {
		List<Product> products = this.getProductsRepository.execute(filter, sort, limit, offset);
		return products.stream()
				.map(this::buildProductResponse)
				.collect(Collectors.toList());
	}
	
	private ProductResponse buildProductResponse(Product product) {
		return new ProductResponse(product.getName(),
				product.getCategory(),
				product.getPrice(),
				product.getQuantity(),
				product.getStatus());
	}

}
