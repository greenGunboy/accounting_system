package order;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import menu.form.ProductBean;
import order.form.OrderBean;
import order.form.OrderForm;

public class InputAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("back", "backPage");
		map.put("order", "orderPage");
		return map;
	}


	/**
	 * 「戻る」ボタン押下時処理
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("backControl");
	}


	/**
	 * 「注文」ボタン押下時、注文数量0以上の注文データをパラメータへセット
	 *
	 * @param mapping
	 * @param form 注文ID、注文商品名、注文商品ID、注文数量（ORDER_ID、PRODUCT_ID、PRODUCT_NAME、QUANTITY）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward orderPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrderForm frm = (OrderForm)form;
		HttpSession session = request.getSession();
		List<OrderBean> list = new ArrayList<OrderBean>();


		// 選択された注文数量をBeanへセット（商品区分１のデータ）
		List<ProductBean> food = (List<ProductBean>) session.getAttribute("foodList");
		for (int i = 0; i < food.size(); i++) {
			ProductBean bn = food.get(i);
			bn.setQuantity(frm.getRequestquantity()[i]);
		}
		// 選択された注文数量をBeanへセット（商品区分２のデータ）
		List<ProductBean> drink  = (List<ProductBean>) session.getAttribute("drinkList");
		for (int i = 0; i < drink.size(); i++) {
			ProductBean bn = drink.get(i);
			bn.setQuantity(frm.getRequestquantity()[i + food.size()]);
		}

/*****************************注文数量の未選択チェック**********************************/

		int checkQuantity = 0;
		if (frm.getRequestquantity() != null) {
			for (int quantity : frm.getRequestquantity()) {
				checkQuantity += quantity;
			}
			if (checkQuantity == 0) {
				ActionMessages errors = new ActionMessages();
				errors.add("requestQuantity_error", new ActionMessage("error.requestQuantity.exist"));
				saveErrors(request, errors);
				return mapping.findForward("error");
			}
		} else {
			ActionMessages errors = new ActionMessages();
			errors.add("requestQuantity_error", new ActionMessage("error.requestQuantity.notexist"));
			saveErrors(request, errors);
			return mapping.findForward("error");
		}

/***************************************************************************************/

		// 注文商品名の数だけ
		for (int i = 0; i <= frm.getRequestname().length - 1; i++) {
			if (frm.getRequestquantity()[i] != 0) {
				OrderBean bn = new OrderBean();
				bn.setOrderID(frm.getOrderID());
				bn.setOrderproductname(frm.getRequestname()[i]);
				bn.setOrderproductID(frm.getRequestproductID()[i]);
				bn.setOrderquantity(frm.getRequestquantity()[i]);
				// 注文数量と価格を乗算し、合計金額値をbeanにセット
				bn.setTotalfee(frm.getRequestquantity()[i] * frm.getReuqestprice()[i]);
				list.add(bn);
			}
		}
		session.setAttribute("requestList", list);
		return mapping.findForward("nextConf");
	}

}
