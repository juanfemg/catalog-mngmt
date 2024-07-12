package co.com.stockap.catalog.application.dto;

import java.util.Objects;

public record ProductStatusRequest(String status) {
	public ProductStatusRequest {
		Objects.requireNonNull(status);
	}
}
