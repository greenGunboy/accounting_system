package order.form;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.util.LabelValueBean;
import org.apache.struts.validator.ValidatorForm;

public class OrderForm extends ValidatorForm {

	private String guestname;
	private String orderproductname;
	private int orderquantity;
	private int orderID;
	private int orderI_ID;
	private String[] requestname;
	private int[] reuqestprice;
	private int[] requestproductID;
	private int[] requestquantity;
	private int totalfee;
	private List<LabelValueBean> quantityList;

	public OrderForm () {
		quantityList = new ArrayList<LabelValueBean>();
		for (int i = 1; i <= 20; i ++) {
			quantityList.add(new LabelValueBean(String.valueOf(i), String.valueOf(i)));
		}
	}

	public String getGuestname() {
		return guestname;
	}
	public void setGuestname(String guestname) {
		this.guestname = guestname;
	}
	public String getOrderproductname() {
		return orderproductname;
	}
	public void setOrderproductname(String orderproductname) {
		this.orderproductname = orderproductname;
	}
	public int getOrderquantity() {
		return orderquantity;
	}
	public void setOrderquantity(int orderquantity) {
		this.orderquantity = orderquantity;
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
	public String[] getRequestname() {
		return requestname;
	}
	public void setRequestname(String[] requestname) {
		this.requestname = requestname;
	}
	public int[] getReuqestprice() {
		return reuqestprice;
	}
	public void setReuqestprice(int[] reuqestprice) {
		this.reuqestprice = reuqestprice;
	}
	public int[] getRequestproductID() {
		return requestproductID;
	}
	public void setRequestproductID(int[] requestproductID) {
		this.requestproductID = requestproductID;
	}
	public int[] getRequestquantity() {
		return requestquantity;
	}
	public void setRequestquantity(int[] requestquantity) {
		this.requestquantity = requestquantity;
	}
	public int getTotalfee() {
		return totalfee;
	}
	public void setTotalfee(int totalfee) {
		this.totalfee = totalfee;
	}
	public List<LabelValueBean> getQuantityList() {
		return quantityList;
	}
	public void setQuantityList(List<LabelValueBean> quantityList) {
		this.quantityList = quantityList;
	}

	public void setClear () {
		this.guestname = "";
	}

	/**
	 * 顧客名の未記入チェックをするメソッド
	 */
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request) {

		if (request.getParameter("pageControl").equals("追加")) {
			ActionErrors errors = super.validate(mapping, request);
			if (guestname.equals("")) {
				errors.add("guestname_required", new ActionMessage("error.guestname.required"));
			}
			return errors;
		} else {
			return null;
		}
	}


}
