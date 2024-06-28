package co.com.stockap.catalog.application.dto;

import java.util.Objects;

public record ProductRequest(String name, String category, double price, int quantity, String status) {
	public ProductRequest {
		Objects.requireNonNull(name);
		Objects.requireNonNull(price);
	}
}
