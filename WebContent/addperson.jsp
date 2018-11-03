<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>添加人员信息</title>
</head>
<body>
	<h1>添加人员信息</h1>
	<form name="addperson" id="addperson"
		action="ProcessInformation1?method=add" method="post" onsubmit = "return add_sure()">
		<table>
			<tr>
				<td>客户ID</td>
				<td><input type="text" id="customerId" name="customerID"></td>
			</tr>

			<tr>
				<td>客户姓名</td>
				<td><input type="text" id="customerName" name="customerName">
				</td>
			</tr>

			<tr>
				<td>性别</td>
				<td><input type="radio" name="sex" value="男" checked="checked" />男
					<input type="radio" value="女" name="sex" /> 女</td>
			</tr>

			<tr>
				<td>职业</td>
				<td><input type="text" id="occupation" name="occupation"></td>
			</tr>

			<tr>
				<td>文化程度</td>
				<td><select name="eduLevel">
						<option value="小学">小学</option>
						<option value="初中">初中</option>
						<option value="高中">高中</option>
						<option value="大专">大专</option>
						<option value="本科">本科</option>
						<option value="研究生">研究生</option>
						<option value="博士">博士</option>
				</select></td>
			</tr>

			<tr>
				<td>住址</td>
				<td><input type="text" id="address" size="30" name="address" />
				</td>
			</tr>

			<tr>
				<!--Two buttons, one is used to redirect to the servlet to load the main window, another is used to exit.-->
				<td><input type="submit" value="保存" id="add"> <input
					type="button" value="返回" id="return"
					onclick="location.href='http://localhost:8888/Experiment1/welcome.jsp'"></td>
		</table>
	</form>
	<script>
		function add_sure(){
			var add_sure = confirm("确认添加？");
			return add_sure;
		}
	</script>
</body>
</html>