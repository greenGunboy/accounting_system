<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title>グラフメニュー</title>
</head>
<body>
	<h1>グラフメニュー</h1>

	<html:errors />

	<html:form action = "/graph">
		<table align = "center">
			<tr>
				<td>
					<i class="fa fa-bar-chart fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "salesGraph" />
						</html:submit>
					</i>
				</td>
			</tr>
			<tr>
				<td>
					<i class="fa fa-pie-chart fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "menuGraph" />
						</html:submit>
					</i>
				</td>
			</tr>
			<tr>
				<td>
					<i class="fa fa-reply fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "back" />
						</html:submit>
					</i>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>