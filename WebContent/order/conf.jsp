<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title>注文確認</title>
</head>
<body>
	<h1>注文確認</h1>

	<div id = "login">
		<html:errors />

		<html:form action = "/orderConf">
			<table align = "center">
				<logic:iterate id = "requestData" name = "requestList" type = "order.form.OrderBean">
					<tr>
						<td>
							<bean:write name = "requestData" property = "orderproductname" />
							×<bean:write name = "requestData" property = "orderquantity" />
						</td>
					</tr>
				</logic:iterate>
			</table>

			<h4>上記内容で注文を確定します。</h4>
			<html:submit property = "pageControl" styleClass = "button" >
				<bean:message key = "back" />
			</html:submit>
			<html:submit property = "pageControl" styleClass = "button" >
				<bean:message key = "order" />
			</html:submit>
		</html:form>
	</div>
</body>
</html:html>