package order;

import java.sql.Connection;
import java.sql.SQLException;
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
import order.form.OrderBean;
import order.form.OrderForm;

public class GuestAddAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("back", "backPage");
		map.put("add", "addPage");
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
	 * 「追加」ボタン押下時、入力された顧客名をORDER_Hテーブルへ登録するメソッド
	 * 登録後、再度、ORDER_Hテーブルから未会計顧客リストを読み込みパラメータへセット
	 *
	 * @param mapping
	 * @param form 顧客名（guestName）
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward addPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		OrderForm frm = (OrderForm)form;
		Connection con = null;
		DataSource source = getDataSource(request);
		int id = (int) session.getAttribute("id");
		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			DBAccess db = new DBAccess();

/******************「注文一覧の顧客名」と「入力された顧客名」が同じかチェック********************/
			List<OrderBean> list = db.serchOrderGuestList(con, id);
			for (OrderBean checkGuestName : list) {
				if (checkGuestName.getGuestname().equals(frm.getGuestname())) {
					ActionMessages errors = new ActionMessages();
					errors.add("guestname_error", new ActionMessage("error.guestname.exist"));
					saveErrors(request, errors);
					return mapping.findForward("backGuestAdd");
				}
			}
/***********************************************************************************************/

			db.insertNewGuest(frm, con, id);
			list = db.serchOrderGuestList(con, id);
			session.setAttribute("guestList", list);
			frm.setClear();
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
