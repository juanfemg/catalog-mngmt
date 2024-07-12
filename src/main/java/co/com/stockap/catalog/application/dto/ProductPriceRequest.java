package co.com.stockap.catalog.application.dto;

import java.util.Objects;

public record ProductPriceRequest(double price) {
	public ProductPriceRequest {
		Objects.requireNonNull(price);
	}

}
