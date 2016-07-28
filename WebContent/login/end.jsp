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
	<title><bean:message key = "end_title" /></title>
</head>
<body>
	<h1><bean:message key = "end_title" /></h1>

	<div id = "login">

		<html:errors />

		<html:form action = "/loginEnd">
			<table align = "center">
				<tr>
					<td><bean:message key = "login_ID" /></td>
					<td>
						<bean:write name = "loginform" property = "login" />
					</td>
				</tr>
			</table>
			<p><bean:message key = "conp_info" /></p>
			<i class="fa fa-list fa-3x" aria-hidden="true">
				<html:submit property = "pageControl" styleClass = "button" >
						<bean:message key = "toMenu" />
				</html:submit>
			</i>
			<i class="fa fa-sign-out fa-3x" aria-hidden="true">
				<html:submit property = "pageControl" styleClass = "button" >
					<bean:message key = "logout" />
				</html:submit>
			</i>
		</html:form>
	</div>
</body>
</html:html>