package co.com.stockap.catalog.application.entrypoint.impl;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import co.com.stockap.catalog.application.dto.ProductResponse;
import co.com.stockap.catalog.application.entrypoint.Entrypoint;
import co.com.stockap.catalog.application.usecase.GetProducts;

@RestController
@RequestMapping("/products")
public class GetProductsEntrypoint extends Entrypoint {
	
	private GetProducts getProducts;
	private static final String RESPONSE_NOT_FOUND_MESSAGE = "Products do not exist";
	
	public GetProductsEntrypoint(GetProducts getProducts) {
		this.getProducts = getProducts;
	}

	@GetMapping
	public ResponseEntity<Object> handle(@RequestParam Map<String,String> params) {
		this.validateMissingQueryParams(params, "filter", "limit", "offset");
		
		String filter = this.queryParam(params, "filter");
		String sort = this.queryParam(params, "sort");
		Integer limit = Integer.parseInt(this.queryParam(params, "limit"));
		Integer offset = Integer.parseInt(this.queryParam(params, "offset"));
		
		Map<String,Object> filterMap = this.getObjectMap(filter);
		Map<String,Object> sortMap = this.getObjectMap(sort);
		
		List<ProductResponse> response = this.getProducts.execute(filterMap, sortMap, limit, offset);
		if (response.isEmpty()) {
			return this.buildResponse(HttpStatus.NOT_FOUND, RESPONSE_NOT_FOUND_MESSAGE);
		}
		return this.buildResponse(HttpStatus.OK, response);
	}
	
	private Map<String,Object> getObjectMap(String param) {
		if (Objects.isNull(param)) {
			return null;
		}
		return Arrays.stream(param.split(","))
				.map(kv -> kv.split(":", 2))
				.filter(kvArray -> kvArray.length == 2)
				.collect(Collectors.toMap(kv -> kv[0], kv -> kv[1]));
	}

}
