<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ page import = "java.util.*" %>
<%@ page import = "graph.form.GraphBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title>����f�[�^</title>
</head>
<body>
	<h1>����f�[�^</h1>

	<div id = "login">

		<logic:empty name = "salesMonthly" >
			�f�[�^������܂���
		</logic:empty>


		<logic:notEmpty name = "salesGraph">
			<script type="text/javascript" src="https://www.google.com/jsapi"></script>
			<script type="text/javascript">
			google.load("visualization", "1", {packages:["corechart"]});
			google.setOnLoadCallback(
					function() {
					var data = google.visualization.arrayToDataTable([
							[       '', '���㍂'],
							<% List<GraphBean> list = (List<GraphBean>)session.getAttribute("salesGraph");
							String y = (String)session.getAttribute("year");
							String m = (String)session.getAttribute("month");
							int length = 0;
							for (GraphBean data : list) {
								length ++;
							}
							int i = 0;
							for (GraphBean data : list) { %>
							['<%= data.getDay() %>��', <%= data.getTotalSales() %>]
								<% if (i != length) { %>
								,
							<% }
							i ++;
							}
							%>
						]);

					var options = {
							title: '���ʔ���O���t',
							hAxis: {title: '<%= y %>�N <%= m %>��'}
					};
					options['showRowNumber'] = true;

					var chart = new google.visualization.ColumnChart(document.getElementById('salesGraph'));
					chart.draw(data, options);
					}
					);
			</script>
			<div id = "salesGraph" style="width:85%; height:250pt" align = "center"></div>
		</logic:notEmpty>

		<% String year = ""; %>
		<logic:present name = "salesMonthly">
			<div class="boxList">
				<logic:iterate id = "salesData" name = "salesMonthly" type = "graph.form.GraphBean">
					<logic:notEqual name = "salesData" property = "year" value = "<%= year %>" >
						<div class = "box">
							<table border = "2">
								<tr>
									<th colspan = "3"><bean:write name = "salesData" property = "year" />�N</th>
								</tr>
								<tr>
									<th>��</th>
									<th>���㍂</th>
									<th>�g��</th>
								</tr>
								<logic:iterate id = "salesDataMonth" name = "salesMonthly" type = "graph.form.GraphBean">
									<logic:equal name = "salesDataMonth" property = "year" value = "<%= salesData.getYear() %>" >
										<tr>
											<td>
												<bean:write name = "salesDataMonth" property = "month" />
												<html:form action = "/graphSales">
													<html:submit property = "pageControl" styleClass = "minibutton" >
														<bean:message key = "see" />
													</html:submit>
													<input type = "hidden" name = "year" value = "<bean:write name = "salesDataMonth" property = "year" />" />
													<input type = "hidden" name = "month" value = "<bean:write name = "salesDataMonth" property = "month" />" />
												</html:form>
											</td>
											<td>\<bean:write name = "salesDataMonth" property = "totalSales" /></td>
											<td><bean:write name = "salesDataMonth" property = "visitor" /></td>
										</tr>
									</logic:equal>
								</logic:iterate>
							</table>
						</div>
					<% year = salesData.getYear(); %>
					</logic:notEqual>
				</logic:iterate>
			</div>
		</logic:present>

		<html:form action = "/graphSales">
			<html:submit property = "pageControl" styleClass = "button" >
				<bean:message key = "back" />
			</html:submit>
		</html:form>
	</div>
</body>
</html:html>