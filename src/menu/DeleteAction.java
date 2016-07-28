package menu;

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
import org.apache.struts.actions.LookupDispatchAction;

import db.DBAccess;
import menu.form.MenuForm;
import menu.form.ProductBean;

public class DeleteAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("delete", "deletePage");
		map.put("back", "backControl");
		return map;
	}


	/**
	 * productテーブルから選択された商品データを削除し、
	 * 再度、商品データを読み込み食べ物、飲み物に分けパラメータにセット
	 *
	 * @param mapping
	 * @param form 選択されたproductID
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward deletePage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {
		MenuForm frm = (MenuForm)form;
		Connection con = null;
		DataSource source = getDataSource(request);
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		try {
			con = source.getConnection();
			con.setAutoCommit(false);
			DBAccess db = new DBAccess();
			db.deleteProductInfoOrder_I(frm, con);
			db.deleteProductInfoProduct (frm, con);
			List<ProductBean> list = db.serchFoodProductInfo(id, con);
			session.setAttribute("foodList", list);
			list = db.serchDrinkProductInfo(id, con);
			session.setAttribute("drinkList", list);
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
		frm = new MenuForm();
		session.setAttribute("menuform", frm);
		return mapping.findForward("delete");
	}


	/**
	 * 「戻る」ボタン押下時、form内を初期化し前画面へ遷移する
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward backControl (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		MenuForm frm = new MenuForm();
		session.setAttribute("menuform", frm);
		return mapping.findForward("backControl");
	}

}
