<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="../font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="../CSS/main.css" type="text/css">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title><bean:message key = "login" /></title>
</head>
<body>

	<div id = "title">
	   <h1>Accounting System</h1>
	</div>

	<div id = "login">
	    <h1>ログインデータを入力してください</h1>

		<html:errors />

		<html:form action = "/login">
			<table align = "center">
				<tr>
					<td><bean:message key = "login_ID" /></td>
					<td>
						<html:text property = "login" maxlength = "8" styleClass = "text" />
					</td>
				</tr>
				<tr>
					<td><bean:message key = "passwd" /></td>
					<td>
						<html:password property = "passwd" maxlength = "8" styleClass = "text" />
					</td>
				</tr>
			</table>

			<i class="fa fa-sign-in fa-3x" aria-hidden="true">
				<html:submit property = "pageControl" styleClass = "button" >
					<bean:message key = "login" />
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