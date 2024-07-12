package co.com.stockap.catalog.application.usecase.impl;

import java.util.Arrays;
import java.util.Optional;

import org.springframework.stereotype.Service;

import co.com.stockap.catalog.application.dto.ProductStatusRequest;
import co.com.stockap.catalog.application.exception.MappingStatusException;
import co.com.stockap.catalog.application.usecase.ChangeProductStatus;
import co.com.stockap.catalog.domain.enums.StatusEnum;
import co.com.stockap.catalog.domain.repository.ChangeProductStatusRepository;

@Service
public class ChangeProductStatusDefault implements ChangeProductStatus {

	private ChangeProductStatusRepository changeProductStatusRepository;
	
	public ChangeProductStatusDefault(ChangeProductStatusRepository changeProductStatusRepository) {
		this.changeProductStatusRepository = changeProductStatusRepository;
	}
	
	@Override
	public long execute(String id, ProductStatusRequest request) {
		Optional<StatusEnum> status = this.checkStatus(request);
		if (status.isEmpty()) {
			throw new MappingStatusException(String.format("Failed bacause of %s not matching a valid status", request.status()));
		}
		
		return this.changeProductStatusRepository.execute(id, status.get());
	}
	
	private Optional<StatusEnum> checkStatus(ProductStatusRequest request) {
		return Arrays.stream(StatusEnum.values())
				.filter(status -> status.name().equalsIgnoreCase(request.status()))
				.findFirst();
	}

}
