package graph;

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
import graph.form.GraphBean;
import graph.form.GraphForm;

public class SalesGraphAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("back", "backPage");
		map.put("see", "seePage");
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
	 * 「」押下時、グラフに使用するデータをパラメータにセットする。
	 * ORDER_Hテーブルから会計日時と日別の合計金額を取得するメソッド
	 *
	 * @param mapping
	 * @param form 選択された年と月のデータ
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward seePage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		HttpSession session = request.getSession();
		GraphForm frm = (GraphForm)form;
		int id = (int) session.getAttribute("id");
		Connection con = null;
		DataSource source = getDataSource(request);
		// グラフタイトルに表示する用の動的変数
		String y = "";
		String m = "";
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			List<GraphBean> list = db.serchSalesGraphInfo(con, frm, id);
			session.setAttribute("salesGraph", list);
			for (GraphBean graphDate : list) {
				y = graphDate.getYear();
				m = graphDate.getMonth();
			}
			session.setAttribute("year", y);
			session.setAttribute("month", m);
		} catch (SQLException ex) {
			ex.printStackTrace();
			throw ex;
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("see");
	}

}
