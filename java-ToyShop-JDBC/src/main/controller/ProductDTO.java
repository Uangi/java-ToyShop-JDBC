package main.controller;

public class ProductDTO {		// Data Transfer Object - 데이터를 전달만 해주는 역할
	// Value Object(VO) - 데이터를 가지고 있는 객체
	
//	private String order_id;
//	private String order_pwd;
	
	private String productId;
	private String productName;
	private int price;
	private int quantity;
	private String order_id;
	private String order_pwd;
	private int totalprice;
	
	public int getTotalprice() {
		return totalprice;
	}
	
	public void setTotalprice(int totalprice) {
		this.totalprice = totalprice;
	}
	
	public String getId() {
		return order_id;
	}
	
	public String getPwd() {
		return order_pwd;
	}
	
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
	
	@Override
	public String toString() {
		String str;
		str = String.format("%8s %17s %14d 원 %8d", productId, productName, price, quantity);
		return str;
	}
	
	public String toString2() {
		String str;
		str = String.format("%8s %17s %14d 원 %8d %8d", productId, productName, price, quantity, totalprice);
		return str;
	}
	
}
