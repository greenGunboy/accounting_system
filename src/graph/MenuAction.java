package graph;

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
import graph.form.GraphBean;

public class MenuAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("salesGraph", "graphPage");
		map.put("menuGraph", "menuManagePage");
		map.put("back", "backPage");
		return map;
	}


	/**
	 * 「売上データ」押下時、注文詳細リストに表示するデータをパラメータにセット
	 * ORDER_Hテーブルから会計日時、月別の総売り上げ、来客組数を取得するメソッド
	 *
	 * グラフに使用されるデータは初期化してから次画面へ遷移する
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward graphPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		Connection con = null;
		DataSource source = getDataSource(request);
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			List<GraphBean> list = new ArrayList<GraphBean>();
			session.setAttribute("salesGraph", list);
			list = db.serchSalesMonthly(con, id);
			session.setAttribute("salesMonthly", list);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("salesGraph");
	}


	/**
	 * 「メニューデータ」押下時、ORDER_H、ORDER_i、PRODUCTテーブルから
	 * 商品名、商品ごとの総売上、総注文数（PRODUCT_NAME,SUM(AMOUNT) * QUANTITY, SUM(QUANTITY)）
	 * 取り出し、パラメータへセット
	 * menuGraph.jspへ遷移するメソッド
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward menuManagePage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		int id = (int) session.getAttribute("id");
		Connection con = null;
		DataSource source = getDataSource(request);
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			List<GraphBean> list = db.serchFoodGraphInfo(con, id);
			session.setAttribute("foodGraph", list);
			list = db.serchDrinkGraphInfo(con, id);
			session.setAttribute("drinkGraph", list);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("menuGraph");
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

}
