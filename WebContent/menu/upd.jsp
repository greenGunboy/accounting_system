<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri = "/WEB-INF/struts-bean.tld" prefix = "bean" %>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>

	<script type="text/javascript"><!--
	function myEnter(){
		if (window.confirm('修正しますか？')) {
			return true;
		} else {
			return false;
		}
	}
	// --></script>

	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title>メニュー修正</title>
</head>
<body>
	<h1>メニュー修正</h1>
	<div id = "login">

		<html:errors />

		<html:form action = "/menuUpd">
			<table align = "center">
				<tr>
					<td>商品名：</td>
					<td>
						<html:text property = "productname" styleClass = "text" />
					</td>
				</tr>
				<tr>
					<td>価格：</td>
					<td>
						<html:text property = "price" styleClass = "text" />
					</td>
				</tr>
				<tr>
					<td>商品区分：</td>
					<td>
						<html:radio property = "producttype" value = "1" />フード
					</td>
					<td>
						<html:radio property = "producttype" value = "2" />ドリンク
					</td>
				</tr>
			</table>
			<i class="fa fa-reply fa-3x" aria-hidden="true">
				<html:submit property = "pageControl" styleClass = "button" >
					<bean:message key = "back" />
				</html:submit>
			</i>
			<i class="fa fa-pencil fa-3x" aria-hidden="true">
			<html:submit property = "pageControl" onclick="return myEnter()" styleClass = "button">
				<bean:message key = "upd" />
			</html:submit>
			</i>
		</html:form>
	</div>
</body>
</html:html>