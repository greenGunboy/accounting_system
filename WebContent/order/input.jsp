<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<jsp:useBean id="bn" scope="session" class="order.form.OrderBean" />
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title>メニュー一覧</title>
</head>
<body>
	<h1>メニュー一覧</h1>
	<div id = "login">

		<html:errors />

		<logic:empty name = "foodList">登録されたFOODデータはありません。</logic:empty><br />
		<logic:empty name = "drinkList">登録されたDRINKデータはありません。</logic:empty>

		<html:form action = "/orderInput" >
			<div class = "boxList">
				<logic:notEmpty name = "foodList">
					<div class = "box">
						<table border = "1">
							<tr>
								<th colspan = "5" >フードリスト</th>
							</tr>
							<tr>
								<th><bean:message key = "productname" /></th>
								<th>注文数量</th>
							</tr>
							<logic:iterate id = "foodData" name = "foodList" type = "menu.form.ProductBean">
								<tr>
									<td>
										<bean:write name = "foodData" property = "productname" />
										<input type = "hidden" name = "requestname" value = "<bean:write name = "foodData" property = "productname" />" />
										<input type = "hidden" name = "requestproductID" value = "<bean:write name = "foodData" property = "productID" />" />
										<input type = "hidden" name = "reuqestprice" value = "<bean:write name = "foodData" property = "price" />" />
									</td>
									<td>
										<select name = "requestquantity" >
											<option value = ""></option>
											<% for (int i = 1; i <= 20; i++) { %>
											<option value = "<%= i %>"
											<% if (foodData.getQuantity() == i) { %>
												selected = "selected"
											<% } %>
											><%= i %>
											</option>
											<% } %>
										</select>
										<!-- <%--
										<html:select property = "requestquantity" >
											<html:option value = "<%= String.valueOf(foodData.getQuantity()) %>">
													<html:optionsCollection property = "quantityList" />
											</html:option>
										</html:select> --%> -->
									</td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</logic:notEmpty>

				<logic:notEmpty name = "drinkList">
					<div class = "box">
						<table border = "1">
							<tr>
								<th colspan = "5" >ドリンクリスト</th>
							</tr>
							<tr>
								<th><bean:message key = "productname" /></th>
								<th>注文数量</th>
							</tr>
							<logic:iterate id = "drinkData" name = "drinkList" type = "menu.form.ProductBean">
								<tr>
									<td>
										<bean:write name = "drinkData" property = "productname" />
										<input type = "hidden" name = "requestname" value = "<bean:write name = "drinkData" property = "productname" />" />
										<input type = "hidden" name = "requestproductID" value = "<bean:write name = "drinkData" property = "productID" />" />
										<input type = "hidden" name = "reuqestprice" value = "<bean:write name = "drinkData" property = "price" />" />
									</td>
									<td>
										<select name = "requestquantity" >
											<option value = ""></option>
											<% for (int i = 1; i <= 20; i++) { %>
											<option value = "<%= i %>"
											<% if (drinkData.getQuantity() == i) { %>
												selected = "selected"
											<% } %>
											><%= i %>
											</option>
											<% } %>
										</select>
									</td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</logic:notEmpty>
			</div>
			<br />
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