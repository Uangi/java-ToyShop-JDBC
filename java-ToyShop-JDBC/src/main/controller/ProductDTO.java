package main.controller;

public class ProductDTO {		// Data Transfer Object - 데이터를 전달만 해주는 역할
	// Value Object(VO) - 데이터를 가지고 있는 객체
	

	
	private String productId;
	private String productName;
	private int CartId;
	private int price;
	private int quantity;
	private int totalprice;
	
	
	public String getProductId() {
		return productId;
	}

	public void setProductId(String productId) {
		this.productId = productId;
	}

	public String getProductName() {
		return productName;
	}

	public void setProductName(String productName) {
		this.productName = productName;
	}

	public int getCartId() {
		return CartId;
	}

	public void setCartId(int cartId) {
		CartId = cartId;
	}
	
	public int getPrice() {
		return price;
	}

	public void setPrice(int price) {
		this.price = price;
	}

	public int getQuantity() {
		return quantity;
	}

	public void setQuantity(int quantity) {
		this.quantity = quantity;
	}
	
	public int getTotalprice() {
		return totalprice;
	}
	
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	
	@Override
	public String toString() {
		String str;
		str = String.format("%8s %17s %14d 원 %8d", productId, productName, price, quantity);
		return str;
	}
	
	public String toString2() {
		String str;
		str = String.format("%s %17s %14d 원 %8d %8d", productId != null ? productId : "", productName, price, quantity, totalprice);
		return str;
	}
	
}