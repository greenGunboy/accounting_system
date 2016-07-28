package login;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.sql.DataSource;

import org.apache.struts.action.ActionForm;
import org.apache.struts.action.ActionForward;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.actions.LookupDispatchAction;

import db.DBAccess;
import login.form.LoginForm;

public class ConfAction extends LookupDispatchAction {

	@Override
	protected Map<String, String> getKeyMethodMap() {
		Map<String, String> map = new HashMap<String, String>();
		map.put("register", "registerPage");
		map.put("back", "backPage");
		return map;
	}


	/**
	 * 入力されたログイン登録情報をLOGINテーブルへ登録する
	 *
	 * @param mapping
	 * @param form
	 * @param request
	 * @param response
	 * @return DBアクセスに不具合があればエラー画面へ。登録処理が完了すればlogin.end.jspheへ遷移する
	 * @throws Exception
	 */
	public ActionForward registerPage (ActionMapping mapping,ActionForm form,HttpServletRequest request,HttpServletResponse response) throws Exception {

		LoginForm frm = (LoginForm)form;
		DataSource source = getDataSource(request);
		Connection con = null;
		try {
			con = source.getConnection();
			DBAccess db = new DBAccess();
			db.insertLoginInfo(frm, con);
		} catch (SQLException ex) {
			ex.printStackTrace();
			return mapping.findForward("error");
		} finally {
			if (con != null) {
				con.close();
			}
		}
		return mapping.findForward("nextEnd");
	}


	/**
	 * conf.jspで「戻る」ボタン押下時、
	 * input.jspへ遷移させるメソッド
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
