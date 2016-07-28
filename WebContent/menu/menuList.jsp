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
	<title>メニュー編集</title>
</head>
<body>
	<h1>メニュー編集</h1>

	<html:errors />

	<html:form action = "/menuList">

	 	<div id = "login">

			<div class="boxList">
				<div class = "box">
					<table border = "1">
						<tr>
							<th colspan = "3" >フードリスト</th>
							<td>
								<html:submit property = "pageControl" styleClass = "minibutton" >
									<bean:message key = "add" />
								</html:submit>
							</td>
						</tr>
						<tr>
							<th><bean:message key = "productname" /></th>
							<th>価格</th>
						</tr>
						<logic:present name = "foodList">
							<logic:iterate id = "foodData" name = "foodList" type = "menu.form.ProductBean">
								<html:form action = "/menuList" >
									<tr>
										<td>
											<bean:write name = "foodData" property = "productname" />
											<input type = "hidden" name = "productname" value = "<bean:write name = "foodData" property = "productname" />" />
											<input type = "hidden" name = "productID" value = "<bean:write name = "foodData" property = "productID" />" />
										</td>
										<td>
											\<bean:write name = "foodData" property = "price" />
											<input type = "hidden" name = "price" value = "<bean:write name = "foodData" property = "price" />" />
										</td>
										<td>
											<i class="fa fa-pencil" aria-hidden="true">
												<html:submit property = "pageControl" styleClass = "minibutton" >
													<bean:message key = "upd" />
												</html:submit>
											</i>
										</td>
										<td>
											<i class="fa fa-trash" aria-hidden="true">
												<html:submit property = "pageControl" styleClass = "minibutton" >
													<bean:message key = "delete" />
												</html:submit>
											</i>
										</td>
									</tr>
								</html:form>
							</logic:iterate>
						</logic:present>
					</table>
				</div>

				<div class = "box">
					<table border = "1">
						<tr>
							<th colspan = "3" >ドリンクリスト</th>
							<td>
								<html:submit property = "pageControl" styleClass = "minibutton" >
									<bean:message key = "add" />
								</html:submit>
							</td>
						</tr>
						<tr>
							<th><bean:message key = "productname" /></th>
							<th>価格</th>
						</tr>
						<logic:present name = "drinkList">
							<logic:iterate id = "drinkData" name = "drinkList" type = "menu.form.ProductBean">
								<html:form action = "/menuList" >
									<tr>
										<td>
											<bean:write name = "drinkData" property = "productname" />
											<input type = "hidden" name = "productname" value = "<bean:write name = "drinkData" property = "productname" />" />
											<input type = "hidden" name = "productID" value = "<bean:write name = "drinkData" property = "productID" />" />
										</td>
										<td>
											\<bean:write name = "drinkData" property = "price" />
											<input type = "hidden" name = "price" value = "<bean:write name = "drinkData" property = "price" />" />
										</td>
										<td>
											<i class="fa fa-pencil" aria-hidden="true">
												<html:submit property = "pageControl" styleClass = "minibutton" >
													<bean:message key = "upd" />
												</html:submit>
											</i>
										</td>
										<td>
											<i class="fa fa-trash" aria-hidden="true">
												<html:submit property = "pageControl" styleClass = "minibutton" >
													<bean:message key = "delete" />
												</html:submit>
											</i>
										</td>
									</tr>
								</html:form>
							</logic:iterate>
						</logic:present>
					</table>
				</div>
			</div>
			<br />
			<html:submit property = "pageControl" styleClass = "button" >
				<bean:message key = "back" />
			</html:submit>
		</div>
	</html:form>
</body>
</html:html>