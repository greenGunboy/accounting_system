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
import org.apache.struts.action.ActionMessage;
import org.apache.struts.action.ActionMessages;
import org.apache.struts.actions.LookupDispatchAction;

import db.DBAccess;
import login.form.LoginForm;


public class LoginAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("login", "loginPage");
		map.put("register", "registerPage");
		return map;
	}


	/**
	 * 前画面でログインが押下された場合に処理するメソッド
	 * ログイン情報が無ければエラーを表示、あればメニュー画面へ遷移
	 *
	 * @param mapping
	 * @param form 入力されたログインIDとパスワード
	 * @param request
	 * @param response
	 * @return ログインアカウントがあれば次画面（menu.menu.jsp）へ。無ければ遷移させない
	 * @throws Exception
	 */
	public ActionForward loginPage (ActionMapping mapping, ActionForm form, HttpServletRequest request, HttpServletResponse response) throws Exception {

		LoginForm frm = (LoginForm)form;
		DataSource source = getDataSource(request);
		Connection con = null;
		HttpSession session = request.getSession();
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			boolean flg = db.isExistLoginInfo(frm, con);
			if (flg) {
				// ログインアカウントがあればloginIDをパラメータへセット
				// ログインアカウント毎に登録されているメニュー、顧客データを選別するために使用
				int id = db.serchLoginID(frm, con);
				session.setAttribute("id", id);
				return mapping.findForward("login");
			} else {
				ActionMessages errors = new ActionMessages();
				if (frm.getLogin().equals("") || frm.getPasswd().equals("")) {
					errors.add("error_required", new ActionMessage("error.login.input"));
				} else {
					errors.add("login_error", new ActionMessage("error.login.exist"));
				}
				saveErrors(request, errors);
				return mapping.findForward("error");
			}
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
	 * 前画面で「登録」が押下された場合に処理するメソッド
	 * form内をリセットし、登録画面（login.input.jsp）へ遷移する
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward registerPage (ActionMapping mapping,
			ActionForm form,
			HttpServletRequest request,
			HttpServletResponse response) {

		HttpSession session = request.getSession();
		LoginForm frm = new LoginForm();
		session.setAttribute("loginform", frm);
		return mapping.findForward("nextInput");
	}

}
