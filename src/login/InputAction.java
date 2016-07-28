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

public class InputAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("register", "registerPage");
		map.put("back", "backPage");
		map.put("id_check", "checkPage");
		return map;
	}


	/**
	 * input.jspで「ID重複チェック」ボタン押下時に走る
	 * DBアクセスし、ログインIDに重複するデータをチェックするメソッド
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ActionForward checkPage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {

		LoginForm frm = (LoginForm)form;
		DataSource source = getDataSource(request);
		Connection con = null;
		ActionMessages errors = new ActionMessages();
		if (frm.getLogin().equals("")) {
			errors.add("login_required", new ActionMessage("error.login.required"));
		} else {
			try {
				con = source.getConnection();
				DBAccess db = new DBAccess();
				boolean flg = db.isExistLoginID(frm, con);
				if (flg) {
					errors.add("error_id_pass", new ActionMessage("error.id.pass"));
				} else {
					errors.add("check_id_pass", new ActionMessage("check.id.pass"));
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
		saveErrors(request, errors);
		return mapping.findForward("idcheck");
	}


	/**
	 * input.jspで「登録」ボタン押下時に走る
	 * ログインIDとパスワードをLoginFormのvalidateで未記入チェック、ログインID重複チェックし
	 * エラーが無ければend.jspへ遷移するメソッド
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return
	 */
	public ActionForward registerPage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {

		LoginForm frm = (LoginForm)form;
		DataSource source = getDataSource(request);
		Connection con = null;
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			boolean flg = db.isExistLoginID(frm, con);
			if (flg) {
				ActionMessages errors = new ActionMessages();
				errors.add("error_id_pass", new ActionMessage("error.id.pass"));
				saveErrors(request, errors);
				return mapping.findForward("idcheck");
			} else {
				return mapping.findForward("nextConf");
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
	 * input.jspで「戻る」ボタン押下時に走る
	 * formの値を初期化し、login.jspへ遷移するメソッド
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

		HttpSession session = request.getSession();
		LoginForm frm = new LoginForm();
		session.setAttribute("loginform", frm);
		return mapping.findForward("backControl");
	}

}
