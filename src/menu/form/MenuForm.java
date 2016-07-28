package menu.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class MenuForm extends ValidatorForm {

	private String productname;
	private String price;
	private int productID;
	private int producttype;
	private int ID;

	public String getProductname() {
		return productname;
	}
	public void setProductname(String productname) {
		this.productname = productname;
	}
	public String getPrice() {
		return price;
	}
	public void setPrice(String price) {
		this.price = price;
	}
	public int getProductID() {
		return productID;
	}
	public void setProductID(int productID) {
		this.productID = productID;
	}
	public int getProducttype() {
		return producttype;
	}
	public void setProducttype(int producttype) {
		this.producttype = producttype;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}

	/**
	 * 追加、修正、削除ボタン押下時、「商品名」「価格」「商品区分」の未記入チェック
	 * 「価格」のマイナス、且つ0以上、数字かのチェックをするメソッド
	 */
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request) {

		if (request.getParameter("pageControl").equals("追加") ||
				request.getParameter("pageControl").equals("修正") ||
				request.getParameter("pageControl").equals("削除")) {
			ActionErrors errors = super.validate(mapping, request);
			if (productname.equals("")) {
				errors.add("productname_required", new ActionMessage("error.productname.required"));
			}
			if (price.equals("")) {
				errors.add("price_required", new ActionMessage("error.price.required"));
			} else {
				try {
					Integer.parseInt(price);
					if (Integer.parseInt(price) <= 0) {
						errors.add("price_minus", new ActionMessage("error.price.minus"));
					}
				} catch (NumberFormatException e) {
					errors.add("price_integer", new ActionMessage("error.price.integer"));
				}
			}
			if (producttype == 0) {
				errors.add("producttype_required", new ActionMessage("error.producttype.required"));
			}
			return errors;
		} else {
			return null;
		}
	}
}
