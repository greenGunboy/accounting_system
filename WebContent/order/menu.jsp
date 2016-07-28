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
	<title>注文リスト</title>
</head>
<body>
	<div id = "order">
		<h1>注文リスト</h1>

		<html:errors />

		<logic:empty name = "guestList">
			<h1>現在注文リストはありません。</h1>
		</logic:empty>

		<div class="boxList">
			<logic:present name = "guestList">
				<logic:iterate id = "guestData" name = "guestList" type = "order.form.OrderBean">
					<html:form action = "/order">
						<div class = "box">
							<table border = "4" class = "orderbox">
								<tr>
									<th>顧客名</th>
									<th>
										<bean:write name = "guestData" property = "guestname" />
										<input type = "hidden" name = "guestname" value = "<bean:write name = "guestData" property = "guestname" />" />
										<input type = "hidden" name = "orderID" value = "<bean:write name = "guestData" property = "orderID" />" />
									</th>
										<td>
											<i class="fa fa-pencil" aria-hidden="true">
												<html:submit property = "pageControl" styleClass = "minibutton" >
													<bean:message key = "guestUpd" />
												</html:submit>
											</i>
										</td>
										<td>
											<i class="fa fa-trash" aria-hidden="true">
												<html:submit property = "pageControl" styleClass = "minibutton"  onclick = "return myEnter()" >
													<bean:message key = "guestDelete" />
												</html:submit>
											</i>
										</td>
										<td>
										<html:submit property = "pageControl" styleClass = "minibutton" >
											<bean:message key = "add" />
										</html:submit>
										<input type = "hidden" name = "orderID" value = "<bean:write name = "guestData" property = "orderID" />" />
									</td>
								</tr>
								<tr>
									<th><bean:message key = "productname" /></th>
									<th>注文数量</th>
								</tr>
								<logic:present name = "orderList">
									<logic:iterate id = "orderData" name = "orderList" type = "order.form.OrderBean" >
										<logic:equal name = "orderData" property = "guestname" value = "<%= guestData.getGuestname() %>" >
											<html:form action = "/order">
												<tr>
													<td>
														<bean:write name = "orderData" property = "orderproductname" />
														<input type = "hidden" name = "orderproductname" value = "<bean:write name = "orderData" property = "orderproductname" />" />
														<input type = "hidden" name = "orderI_ID" value = "<bean:write name = "orderData" property = "orderI_ID" />" />
													</td>
													<td>
														<bean:write name = "orderData" property = "orderquantity" />
														<input type = "hidden" name = "orderquantity" value = "<bean:write name = "orderData" property = "orderquantity" />" />
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
										</logic:equal>
									</logic:iterate>
									<tr>
										<td colspan = "4"></td>
										<td>
											<html:submit property = "pageControl" styleClass = "minibutton" >
												<bean:message key = "pay" />
											</html:submit>
											<bean:write name = "guestData" property = "totalfee" /> 円
											<input type = "hidden" name = "totalfee" value = "<bean:write name = "guestData" property = "totalfee" />" />
										</td>
									</tr>
								</logic:present>
							</table>
						</div>
						<br />
					</html:form>
				</logic:iterate>
			</logic:present>
		</div>
		<html:form action = "/order">
			<html:submit property = "pageControl" styleClass = "button" >
				<bean:message key = "back" />
			</html:submit>
			<html:submit property = "pageControl" styleClass = "button" >
				<bean:message key = "add_table" />
			</html:submit>
		</html:form>
	</div>
</body>
</html:html>