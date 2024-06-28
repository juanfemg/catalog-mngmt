package co.com.stockap.catalog.application.usecase;

import java.util.List;
import java.util.Map;

import co.com.stockap.catalog.application.dto.ProductResponse;

public interface GetProducts {

	List<ProductResponse> execute(Map<String,Object> filter, Map<String,Object> sort, Integer limit, Integer offset);
}
