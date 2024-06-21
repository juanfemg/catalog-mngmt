package co.com.stockap.catalog.domain.entity;

import java.math.BigInteger;

import co.com.stockap.catalog.domain.enums.StatusEnum;

public class Product {
	private BigInteger id;
	private String name;
	private double price;
	private int quantity;
	private String status;

	public double calculateTotalPrice(double price, double tax) {
		return this.price * tax;
	}

	public boolean hasAvailable(int requestQuantity) {
		return this.quantity >= requestQuantity;
	}

	public boolean isActive() {
		return this.status == StatusEnum.ACTIVE.name();
	}

	public void inactivate() {
		this.status = StatusEnum.INACTIVE.name();
	}

	public BigInteger getId() {
		return id;
	}

	public void setId(BigInteger id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getPrice() {
		return price;
	}

	public void setPrice(double price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public static final class Builder {
		private BigInteger id;
		private String name;
		private double price;
		private int quantity;
		private String status;

		private Builder() {
		}

		public static Builder aProduct() {
			return new Builder();
		}

		public Builder id(BigInteger id) {
			this.id = id;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder price(double price) {
			this.price = price;
			return this;
		}

		public Builder quantity(int quantity) {
			this.quantity = quantity;
			return this;
		}

		public Builder status(String status) {
			this.status = status;
			return this;
		}

		public Product build() {
			Product product = new Product();
			product.setId(id);
			product.setName(name);
			product.setPrice(price);
			product.setQuantity(quantity);
			product.setStatus(status);
			return product;
		}
	}

}
