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

public class UpdAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("back", "backPage");
		map.put("upd", "updPage");
		return map;
	}


	/**
	 * 「戻る」ボタン押下時
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
	 * 「修正」ボタン押下時、ORDER_Iテーブルの数量を修正し、
	 * 再度、ORDER_I、PRODUCTテーブルをJOIN、計算し合計金額抽出後、
	 * ORDER_Hへ合計金額を登録してからパラメータへセット
	 * 注文一覧へ表示
	 *
	 * @param mapping
	 * @param form 修正注文数量、注文明細ID、注文ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updPage (ActionMapping mapping,ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrderForm frm = (OrderForm)form;
		Connection con = null;
		DataSource source = getDataSource(request);
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			DBAccess db = new DBAccess();
			db.updateOrderQuantity(frm, con);
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
		} catch (SQLException ex) {
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
