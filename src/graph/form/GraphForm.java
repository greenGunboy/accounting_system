package graph.form;

import org.apache.struts.action.ActionForm;

public class GraphForm extends ActionForm {

	private String year;
	private String month;

	public String getYear() {
		return year;
	}
	public void setYear(String year) {
		this.year = year;
	}
	public String getMonth() {
		return month;
	}
	public void setMonth(String month) {
		this.month = month;
	}

}
