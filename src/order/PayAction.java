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

public class PayAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("back", "backPage");
		map.put("pay", "payPage");
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
	 * 「会計」ボタン押下時、ORDER_Hテーブルへ会計日時を登録するメソッド
	 * 注文ID（ORDER_ID）と一致するレコードへ登録する
	 *
	 * @param mapping
	 * @param form 注文ID（ORDER_ID）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward payPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		OrderForm frm = (OrderForm)form;
		DataSource source = getDataSource(request);
		int id = (int) session.getAttribute("id");
		Connection con = null;
		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			DBAccess db = new DBAccess();
			db.insertPayment(frm, con);
			List<OrderBean> list = db.serchOrderGuestList(con, id);
			List<Integer> orderIDList = new ArrayList<Integer>();
			if (list != null) {
				// 注文リストは入っていない、名前のみの顧客リストをパラメータへセット
				session.setAttribute("guestList", list);
				// 顧客毎の注文リストを読み込む為、ORDER_IDのリストを作成
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
		return mapping.findForward("pay");
	}

}
