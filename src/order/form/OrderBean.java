package order.form;

public class OrderBean {

	private String guestname;
	private int orderID;
	private int orderI_ID;
	private String orderproductname;
	private int orderproductID;
	private int orderquantity;
	private int price;
	private int totalfee;

	public OrderBean () {
		guestname = "";
		orderproductname = "";
		orderquantity = 0;
		totalfee = 0;
	}

	public String getGuestname() {
		return guestname;
	}
	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}
	public int getOrderID() {
		return orderID;
	}
	public void setOrderID(int orderID) {
		this.orderID = orderID;
	}
	public int getOrderI_ID() {
		return orderI_ID;
	}
	public void setOrderI_ID(int orderI_ID) {
		this.orderI_ID = orderI_ID;
	}
	public String getOrderproductname() {
		return orderproductname;
	}
	public void setOrderproductname(String orderproductname) {
		this.orderproductname = orderproductname;
	}
	public int getOrderproductID() {
		return orderproductID;
	}
	public void setOrderproductID(int orderproductID) {
		this.orderproductID = orderproductID;
	}
	public int getOrderquantity() {
		return orderquantity;
	}
	public void setOrderquantity(int orderquantity) {
		this.orderquantity = orderquantity;
	}
	public int getPrice() {
		return price;
	}
	public void setPrice(int price) {
		this.price = price;
	}
	public int getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(int totalfee) {
		this.totalfee = totalfee;
	}

}
