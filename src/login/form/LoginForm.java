package login.form;

import javax.servlet.http.HttpServletRequest;

import org.apache.struts.action.ActionErrors;
import org.apache.struts.action.ActionMapping;
import org.apache.struts.action.ActionMessage;
import org.apache.struts.validator.ValidatorForm;

public class LoginForm extends ValidatorForm {

	private String login;
	private String passwd;
	private int ID;

	public String getLogin() {
		return login;
	}
	public void setLogin(String login) {
		this.login = login;
	}
	public String getPasswd() {
		return passwd;
	}
	public void setPasswd(String passwd) {
		this.passwd = passwd;
	}
	public int getID() {
		return ID;
	}
	public void setID(int iD) {
		ID = iD;
	}


	/**
	 * ボタン押下時、未記入チェックをするメソッド
	 */
	public ActionErrors validate (ActionMapping mapping, HttpServletRequest request) {

		if (request.getParameter("pageControl").equals("登録")) {
			ActionErrors errors = super.validate(mapping, request);
			if (login.equals("")) {
				errors.add("login_required", new ActionMessage("error.login.required"));
			}
			if (passwd.equals("")) {
				errors.add("passwd_required", new ActionMessage("error.passwd.required"));
			}
			return errors;
		} else {
			return null;
		}
	}
}
