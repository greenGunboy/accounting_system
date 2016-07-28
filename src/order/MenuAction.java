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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import db.DBAccess;
import menu.form.ProductBean;
import order.form.OrderBean;
import order.form.OrderForm;

public class MenuAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("add", "addPage");
		map.put("guestUpd", "guestUpdPage");
		map.put("guestDelete", "guestDeletePage");
		map.put("upd", "updPage");
		map.put("delete", "deletePage");
		map.put("back", "backPage");
		map.put("add_table", "add_tablePage");
		map.put("pay", "payPage");
		return map;
	}


	/**
	 * 「追加」ボタン押下時
	 * 商品リストをproductテーブルから読み込み、食べ物と飲み物に分けてパラメータにセット
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

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
		return mapping.findForward("nextInput");
	}


	/**
	 * 「顧客修正」ボタン押下時、選択された顧客データを修正するメソッド
	 *
	 * 再度、顧客リストを読み込み表示する
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward guestUpdPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("guestUpd");
	}


	/**
	 * 「顧客削除」ボタン押下時、選択された顧客データを削除するメソッド
	 *
	 * 再度、顧客リストを読み込み表示する
	 *
	 * @param mapping
	 * @param form 選択されたORDER_ID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward guestDeletePage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrderForm frm = (OrderForm)form;
		DataSource source = getDataSource(request);
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		Connection con = null;
		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			DBAccess db = new DBAccess();
			db.deleteGuestRequestProduct(con, frm);
			db.deleteGuest(con, frm);

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


	/**
	 * 「修正」ボタン押下時
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward updPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("upd");
	}


	/**
	 * 「削除」ボタン押下時
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward deletePage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {
		return mapping.findForward("delete");
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
	 * 「顧客追加」ボタン押下時
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward add_tablePage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return mapping.findForward("table_add");
	}


	/**
	 * 「会計」ボタン押下時、選択された未会計のレコードを抽出しパラメータにセットするメソッド
	 * 次画面（pay.jsp）に注文商品名、注文数量、合計金額を一覧表示させる
	 *
	 * @param mapping
	 * @param form 注文ID（ORDER_ID）
	 * @param request
	 * @param response
	 * @return pay.jspへ遷移する
	 * @throws Exception
	 */
	public ActionForward payPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		OrderForm frm = (OrderForm)form;
		HttpSession session = request.getSession();
		DataSource source = getDataSource(request);
		Connection con = null;
		int chechPrice = 0;
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			con.setAutoCommit(false);
			List<OrderBean> list = db.serchPaymentInfo(con, frm);
			for (OrderBean check : list) {
				chechPrice += check.getPrice();
			}
			if (chechPrice != 0) {
				session.setAttribute("paymentList", list);
			} else {
				ActionMessages errors = new ActionMessages();
				errors.add("totalfee_error", new ActionMessage("error.totalfee.notExist"));
				saveErrors(request, errors);
				return mapping.findForward("error");
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
