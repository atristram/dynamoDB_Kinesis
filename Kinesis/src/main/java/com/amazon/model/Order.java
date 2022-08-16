package com.amazon.model;

public class Order {
	
	int orderId;
	String product;
	int quantity;

	@Override
	public String toString() {
		return "Order [orderId=" + orderId + ", product=" + product + ", quantity=" + quantity + "]";
	}

	public int getOrderId() {
		return orderId;
	}

	public void setOrderId(int orderId) {
		this.orderId = orderId;
	}

	public String getProduct() {
		return product;
	}

	public void setProduct(String product) {
		this.product = product;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
}
