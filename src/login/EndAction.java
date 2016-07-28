package login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
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

public class EndAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("toMenu", "toMenuPage");
		map.put("logout", "logoutPage");
		return map;
	}


	/**
	 * end.jspで「メニューへ」ボタン押下時に走る
	 * menu.menu.jspへ遷移するメソッド
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward toMenuPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		LoginForm frm = (LoginForm)form;
		DataSource source = getDataSource(request);
		Connection con = null;
		HttpSession session = request.getSession();
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			int id = db.serchLoginID(frm, con);
			session.setAttribute("id", id);
			return mapping.findForward("toMenu");
		} catch (SQLException ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		} finally {
			if (con != null) {
				con.close();
			}
		}
	}


	/**
	 * end.jspで「ログアウト」ボタン押下時に走る
	 * login.login.jspへ遷移するメソッド
	 *
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
