package main.controller;

public class ScoreDTO {		// Data Transfer Object - �����͸� ���޸� ���ִ� ����
	// Value Object(VO) - �����͸� ������ �ִ� ��ü
	
//	private String order_id;
//	private String order_pwd;
	
	private String productId;
	private String productName;
	private int price;
	private int quantity;
	
	
	
	
	public String getProductId() {
		return productId;
	}



	public void setProductId(String productId) {
		this.productId = productId;
	}



	public String getProductName() {
		return productName;
	}



	public int getPrice() {
		return price;
	}



	public void setProductName(String productName) {
		this.productName = productName;
	}



	public void setPrice(int price) {
		this.price = price;
	}



	@Override
	public String toString() {
		String str;
		str = String.format("%8s %8s %4d", productId,productName, price );
		return str;
	}
	
	

}
