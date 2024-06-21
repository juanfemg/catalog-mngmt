package co.com.stockap.catalog.infraestructure.database.enums;

public enum Collection {
	
	PRODUCT("product");
	
	private final String product;
	
	Collection(final String product) {
		this.product = product;
	}
	
	public String getProduct() {
		return this.product;
	}

}
