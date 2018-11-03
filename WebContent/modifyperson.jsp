<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>修改人员信息</title>
</head>
<body>
	<h1>修改人员信息</h1>
	<form action="ProcessInformation1?method=modify" method="post" onsubmit = "return submit_sure()">
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
				<td><input type="text" id="occupatio" name="occupation"></td>
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
				<!--Two buttons, one is used to redirect to the servlet to upload the form, another is used to back to the main window.-->
				<td>
					<!-- create a hidden to store id modified person's id -->
					<input type = "hidden" value=<%=request.getParameter("id")%> name = "modify_value">
					<input type="submit" value="修改" name="modify" >
					<input type="button" value="返回" onclick="goback()">
				</td>
		</table>
	</form>
	<script>
		function goback() {
			history.back();
		}
		function submit_sure(){
			var submit_sure = confirm("确认要修改吗？");
			return submit_sure;
		}
	</script>
</body>
</html>