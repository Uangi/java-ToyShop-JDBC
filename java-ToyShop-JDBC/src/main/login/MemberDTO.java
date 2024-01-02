package main.login;

public class MemberDTO {

	private String orderId;
	private String orderPwd;
	private int money;
	
	public int getMoney() {
		return money;
	}

	public void setMoney(int money) {
		this.money = money;
	}

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
		str = String.format("%8s %8s %8d", orderId, orderPwd, money);
//		System.out.println(orderId + "             " + orderPwd + money);
		return str;
	}
	
	
}
