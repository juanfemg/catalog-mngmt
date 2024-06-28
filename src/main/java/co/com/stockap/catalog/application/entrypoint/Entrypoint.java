package co.com.stockap.catalog.application.entrypoint;

import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Stream;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import co.com.stockap.catalog.application.exception.MissingQueryParamException;
import io.micrometer.common.util.StringUtils;

public abstract class Entrypoint {

	protected <T> ResponseEntity<T> buildResponse(HttpStatus httpStatus, T body) {
		return ResponseEntity.status(httpStatus).body(body);
	}

	protected String queryParam(Map<String, String> params, String value) {
		if (Objects.isNull(params)) {
			return null;
		}
		return Optional.ofNullable(params.get(value)).orElse(null);
	}

	private boolean isMissinQueryParam(Map<String, String> params, String value) {
		if (StringUtils.isBlank(this.queryParam(params, value))) {
			return true;
		}
		return false;
	}

	protected void validateMissingQueryParams(Map<String, String> params, String... values) {
		Stream.of(values).forEach(value -> {
			if (this.isMissinQueryParam(params, value)) {
				throw new MissingQueryParamException(String.format("Required parameter %s is not present", value));
			}
		});
	}

}
