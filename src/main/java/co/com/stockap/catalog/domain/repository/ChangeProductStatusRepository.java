package co.com.stockap.catalog.domain.repository;

import co.com.stockap.catalog.domain.enums.StatusEnum;

public interface ChangeProductStatusRepository {
	
	long execute(String id, StatusEnum status);

}
