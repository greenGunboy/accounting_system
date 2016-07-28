<%@ page language="java" contentType="text/html; charset=Windows-31J"
    pageEncoding="Windows-31J"%>
<%@ taglib uri = "/WEB-INF/struts-html.tld" prefix = "html" %>
<%@ taglib uri="/WEB-INF/struts-bean.tld" prefix="bean"%>
<%@ taglib uri = "/WEB-INF/struts-logic.tld" prefix = "logic" %>
<%@ page import = "java.util.*" %>
<%@ page import = "graph.form.GraphBean" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html:html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=Windows-31J">
	<meta http-equiv="Content-Style-Type" content="text/css" />
	<link href="./font-awesome-4.6.3/css/font-awesome.css" rel="stylesheet">
	<link rel="stylesheet" href="./CSS/main.css" type="text/css">
	<title>メニューランキング</title>
</head>
<body>
	<h1>メニューランキング</h1>

	<div id = "login">
		<logic:empty name = "foodGraph" >
			<logic:empty name = "drinkGraph" >
				データがありません
			</logic:empty>
		</logic:empty>


		<div id = "foodGraph" style = "width: 650px; height:200pt"></div>
		<div id = "drinkGraph" style = "width: 650px; height:200pt"></div>

		<html:form action = "/graphMenu">

			<logic:notEmpty name = "foodGraph" >
				<script type="text/javascript" src="http://www.google.com/jsapi"></script>
				<script type="text/javascript">
					google.load("visualization", "1", {packages:["corechart"]});
					google.setOnLoadCallback(drawChartSamplePie);
					function drawChartSamplePie() {
						var data = google.visualization.arrayToDataTable([
						['タスク', '時間'],
						<% List<GraphBean> list = (List<GraphBean>)session.getAttribute("foodGraph");
							int length = 0;
							for (GraphBean data : list) {
								length ++;
							}
							int i = 0;
							for (GraphBean data : list) { %>
						['<%= data.getProductname() %>', <%= data.getTotalQuantities() %>]
						<% if (i != length) { %>
							,
						<% }
							i ++;
							}
							%>
						]);

						var options = {
								title: 'フードランキング',
								is3D: true
								};
						var chart = new google.visualization.PieChart(document.getElementById('foodGraph'));
						chart.draw(data, options);
						}
				</script>
			</logic:notEmpty>

			<logic:notEmpty name = "drinkGraph" >
				<script type="text/javascript" src="http://www.google.com/jsapi"></script>
				<script type="text/javascript">
					google.load("visualization", "1", {packages:["corechart"]});
					google.setOnLoadCallback(drawChartSamplePie);
					function drawChartSamplePie() {
						var data = google.visualization.arrayToDataTable([
						['タスク', '時間'],
						<% List<GraphBean> list = (List<GraphBean>)session.getAttribute("drinkGraph");
							int length = 0;
							for (GraphBean data : list) {
								length ++;
							}
							int i = 0;
							for (GraphBean data : list) { %>
						['<%= data.getProductname() %>', <%= data.getTotalQuantities() %>]
						<% if (i != length) { %>
							,
						<% }
							i ++;
							}
							%>
						]);

						var options = {
								title: 'ドリンクランキング',
								is3D: true
								};
						var chart = new google.visualization.PieChart(document.getElementById('drinkGraph'));
						chart.draw(data, options);
						}
				</script>
			</logic:notEmpty>

			<div class="boxList">
				<logic:notEmpty name = "foodGraph" >
					<div class = "box">
						<table border = "2" id = "foodRankTable">
							<tr><th colspan = "4">フードランキング</th></tr>
							<tr>
								<th>順位</th>
								<th>商品名</th>
								<th>総注文数</th>
								<th>総売上</th>
							</tr>
							<logic:iterate id = "menuData" name = "foodGraph" type = "graph.form.GraphBean">
								<tr>
									<td><bean:write name = "menuData" property = "rank" /></td>
									<td><bean:write name = "menuData" property = "productname" /></td>
									<td><bean:write name = "menuData" property = "totalQuantities" /></td>
									<td>\<bean:write name = "menuData" property = "totalSales" /></td>
								</tr>
							</logic:iterate>
						</table>
					</div>
				</logic:notEmpty>

				<logic:notEmpty name = "drinkGraph" >
					<div class = "box">
						<table border = "2" id = "drinkRankTable">
							<tr><th colspan = "4">ドリンクランキング</th></tr>
							<tr>
								<th>順位</th>
								<th>商品名</th>
								<th>総注文数</th>
								<th>総売上</th>
							</tr>
							<logic:iterate id = "menuData" name = "drinkGraph" type = "graph.form.GraphBean">
								<tr>
									<td><bean:write name = "menuData" property = "rank" /></td>
									<td><bean:write name = "menuData" property = "productname" /></td>
									<td><bean:write name = "menuData" property = "totalQuantities" /></td>
									<td>\<bean:write name = "menuData" property = "totalSales" /></td>
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
		</html:form>
	</div>
</body>
</html:html>