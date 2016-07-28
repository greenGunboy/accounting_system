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
	<title><bean:message key = "conf_title" /></title>
</head>
<body>
	<h1><bean:message key = "conf_title" /></h1>

	<html:errors />
	<div id = "login">
		<html:form action = "/loginConf">
			<table align = "center">
				<tr>
					<td><bean:message key = "login_ID" /></td>
					<td>
						<bean:write name = "loginform" property = "login" />
					</td>
				</tr>
				<tr>
					<td><bean:message key = "passwd" /></td>
					<td>
						<bean:write name = "loginform" property = "passwd" />
					</td>
				</tr>
			</table>
			<i class="fa fa-reply fa-3x" aria-hidden="true">
				<html:submit property = "pageControl" styleClass = "button" >
					<bean:message key = "back" />
				</html:submit>
			</i>
			<i class="fa fa-child fa-3x" aria-hidden="true">
				<html:submit property = "pageControl" styleClass = "button" >
					<bean:message key = "register" />
				</html:submit>
			</i>
		</html:form>
	</div>
</body>
</html:html>