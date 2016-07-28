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
	<title>メニュー</title>
</head>
<body>
	<h1>メニュー</h1>

	<html:errors />

	<html:form action = "/menu">
		<table align="center">
			<tr>
				<td>
					<i class="fa fa-sign-language fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "order" />
						</html:submit>
					</i>
				</td>
			</tr>
			<tr>
				<td>
					<i class="fa fa-cog fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "menuManage" />
						</html:submit>
					</i>
				</td>
			</tr>
			<tr>
				<td>
					<i class="fa fa-line-chart fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "graph" />
						</html:submit>
					</i>
				</td>
			</tr>
			<tr>
				<td>
					<i class="fa fa-sign-out fa-3x" aria-hidden="true">
						<html:submit property = "pageControl" styleClass = "button" >
							<bean:message key = "logout" />
						</html:submit>
					</i>
				</td>
			</tr>
		</table>
	</html:form>
</body>
</html:html>