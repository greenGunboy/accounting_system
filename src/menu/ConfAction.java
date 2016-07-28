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

public class ConfAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("back", "backPage");
		map.put("register", "confPage");
		return map;
	}

	public ActionForward backPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		return mapping.findForward("backControl");
	}


	/**
	 * 入力された商品データをproductテーブルへ登録するメソッド
	 * 登録後、再度商品データを読み込み、食べ物と飲み物に分け、パラメータにセットする
	 *
	 * @param mapping
	 * @param form 入力された商品データ（商品名、商品区分、価格）。登録後、内包データを初期化し遷移。
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward confPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		MenuForm frm = (MenuForm)form;
		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		Connection con = null;
		DataSource source = getDataSource(request);
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			db.insertNewProductInfo (frm, id, con);
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
		frm = new MenuForm();
		session.setAttribute("menuform", frm);
		return mapping.findForward("nextMenuList");
	}

}
