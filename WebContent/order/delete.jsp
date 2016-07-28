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
		if (window.confirm('削除しますか？')) {
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
	<title>注文削除</title>
</head>
<body>
	<h1>注文削除</h1>

	<div id = "login" >
		<html:errors />
		<div class = "boxList">
			<html:form action = "/orderDelete">
				<div class = "box">
					<table align = "center" border = "1">
						<tr>
							<th><bean:message key = "productname" /></th>
							<th>注文数量</th>
						</tr>
						<tr>
							<td><bean:write name = "orderform" property = "orderproductname" /></td>
							<td>× <bean:write name = "orderform" property = "orderquantity" /></td>
						</tr>
					</table>
				</div>
				<br />
				<br />
				<br />
				<br />
				<h4>以上の注文を削除します</h4>
				<html:submit property = "pageControl" styleClass = "button" >
					<bean:message key = "back" />
				</html:submit>
				<html:submit property = "pageControl" onclick="return myEnter()" styleClass = "button" >
					<bean:message key = "delete" />
				</html:submit>
			</html:form>
		</div>
	</div>
</body>
</html:html>