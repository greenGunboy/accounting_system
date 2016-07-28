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
	<title>会計内容</title>
</head>
<body>
	<h1>会計内容</h1>

	<div id = "login">
		<html:errors />

		<html:form action = "/orderPay">
			<h2><bean:write name = "orderform" property = "guestname"/> 様</h2>
			<table border = "2" align = "center" >
				<tr>
					<th>商品名</th>
					<th>価格</th>
					<th>数量</th>
				</tr>
				<logic:iterate id = "paymentData" name = "paymentList" type = "order.form.OrderBean">
					<tr>
						<td>
							<bean:write name = "paymentData" property = "orderproductname" />
						</td>
						<td>
							<bean:write name = "paymentData" property = "price" />円
						</td>
						<td>
							×<bean:write name = "paymentData" property = "orderquantity" />
						</td>
					</tr>
				</logic:iterate>
				<tr>
					<td colspan = "2"></td>
					<td>
						<bean:write name = "paymentData" property = "totalfee" /> 円
					</td>
				</tr>
			</table>

		<h4>上記内容で会計を確定します。</h4>
			<html:submit property = "pageControl" styleClass = "button">
				<bean:message key = "back" />
			</html:submit>
			<html:submit property = "pageControl" styleClass = "button">
				<bean:message key = "pay" />
			</html:submit>
		</html:form>
	</div>
</body>
</html:html>