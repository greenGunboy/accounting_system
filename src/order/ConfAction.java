package order;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import db.DBAccess;
import order.form.OrderBean;
import order.form.OrderForm;

public class ConfAction extends LookupDispatchAction {

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
	 * 「注文」ボタン押下時、ORDER_Iテーブルへ注文データを登録するメソッド
	 *
	 * ORDER_Iテーブルへ登録後、再度、合計金額を算出し
	 * ORDER_Hテーブルへ合計金額を登録し直す
	 *
	 * 顧客リストを再度読み込み、次画面で一覧表示する
	 *
	 * @param mapping
	 * @param form 注文ID、注文商品ID、注文数量（ORDER_ID、PRODUCT_ID、QUANTITY）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward orderPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrderForm frm = (OrderForm)form;
		HttpSession session = request.getSession();
		List<OrderBean> OrderList = (List<OrderBean>) session.getAttribute("requestList");
		DataSource source = getDataSource(request);
		Connection con = null;
		int id = (int) session.getAttribute("id");
		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			DBAccess db = new DBAccess();
			db.insertRequestOrder_I(OrderList, frm, con);
			int totalfee = db.serchTotalFee(frm, con);
			db.updRequestOrder_H(frm, totalfee, con);

			List<OrderBean> list = db.serchOrderGuestList(con, id);
			List<Integer> orderIDList = new ArrayList<Integer>();
			if (list != null) {
				// 注文リストは入っていない、名前のみの顧客リストをパラメータへセット
				session.setAttribute("guestList", list);
				for (OrderBean orderID : list) {
					orderIDList.add(orderID.getOrderID());
				}
				list = db.serchOrderInfo(con, orderIDList);
				session.setAttribute("orderList", list);
			}
			con.commit();
		}  catch (SQLException ex) {
			con.rollback();
			ex.printStackTrace();
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("nextMenu");
	}

}
