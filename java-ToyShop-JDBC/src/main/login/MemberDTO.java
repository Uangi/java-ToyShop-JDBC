package main.login;

public class MemberDTO {

	private String orderId;
	private String orderPwd;
	
	public String getOrderId() {
		return orderId;
	}

	public String getOrderPwd() {
		return orderPwd;
	}
	
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public void setOrderPwd(String orderPwd) {
		this.orderPwd = orderPwd;
	}
	
	
	@Override
	public String toString() {
		String str;
		str = String.format("%8s %8s", orderId, orderPwd);
		return str;
	}
}
