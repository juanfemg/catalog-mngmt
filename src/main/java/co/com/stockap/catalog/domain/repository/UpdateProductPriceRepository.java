package co.com.stockap.catalog.domain.repository;

public interface UpdateProductPriceRepository {
	
	long execute(String id, double price);

}
