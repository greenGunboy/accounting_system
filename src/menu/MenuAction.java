package menu;

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
import login.form.LoginForm;
import menu.form.ProductBean;
import order.form.OrderBean;

public class MenuAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("order", "orderPage");
		map.put("menuManage", "managePage");
		map.put("graph", "graphPage");
		map.put("logout", "logoutPage");
		return map;
	}


	/**
	 * 「注文」ボタン押下時、未会計の注文リストを読み込みパラメータへセットするメソッド
	 * 次画面（order.menu.jsp）にて注文リストとして顧客名、注文商品名、注文数量、合計金額を表示させる
	 *
	 * @param mapping
	 * @param form まだデータは入っていない
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward orderPage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Connection con = null;
		DataSource source = getDataSource(request);
		int id = (int) session.getAttribute("id");
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
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
		} catch (SQLException ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("nextMenu");
	}


	/**
	 * 「メニュー編集」ボタン押下時、
	 * 商品リストをproductテーブルから読み込み、食べ物と飲み物に分けてパラメータにセット
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward managePage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		Connection con = null;
		DataSource source = getDataSource(request);
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			int id = (int) session.getAttribute("id");
			List<ProductBean> list = db.serchFoodProductInfo(id, con);
			session.setAttribute("foodList", list);
			list = db.serchDrinkProductInfo(id, con);
			session.setAttribute("drinkList", list);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("menuManage");
	}

	public ActionForward graphPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		return mapping.findForward("graph");
	}


	/**
	 * 「ログアウト」押下時、loginform内のログインIDとパスワードを初期化、
	 * ログインIDが入ったパラメータ初期化し遷移する
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward logoutPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		LoginForm frm = new LoginForm();
		session.setAttribute("loginform", frm);
		int id = 0;
		session.setAttribute("id", id);
		return mapping.findForward("logout");
	}

}
