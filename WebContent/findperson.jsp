<%@page import="java.sql.ResultSet"%>
<%@page import="java.util.ArrayList"%>
<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>查看用户信息</title>
</head>
<body>
	<h1>客户列表</h1>
	<br>
	<br>
	<h2>查询条件</h2>
	<br>
	<form action="ProcessInformation1?method=query" method="post">
		<table>
			<!-- User can find a person through the id, or find some persons through the keyword in the name -->
			
			<tr>
				<td>客户ID</td>
				<td><input type="text" name="findByID" id="findByID"></td>
			</tr>
			<tr>
				<td>客户姓名</td>
				<td><input type="text" name="findByName" id="findByName"></td>
			</tr>
			<tr>
				<td></td>
				<td align="right"><input type="submit" name="query" id="query"
					value="查询"></td>
			</tr>
		</table><br><br>
	</form>


	<%
		/*The trouble thought is getting a resultSet, then  travesing the resultSet and storing the people in a list in the servlet, then pass the list to the jsp, then traversing the list to display the person in the jsp, 
			after improving the code, I pass the resultSet to the jsp derectly, and travesing the resultSet to display the people in the jsp.*/
		
			//ArrayList<CustomerInfo> customerInfos = (ArrayList<CustomerInfo>)request.getAttribute("customerList");
		ResultSet resultSet = (ResultSet) request.getAttribute("resultSet");
	%>
	<table cellspacing="0" border="1">
		<tr>
			<td style="width: 50px">id</td>
			<td style="width: 80px">名字</td>
			<td style="width: 50px">性别</td>
			<td style="width: 80px">职业</td>
			<td style="width: 80px">学历</td>
			<td style="width: 200px">地址</td>
			<td style="width: 100px">操作</td>
		</tr>

		<%
			//travesing the resultSet
			while (resultSet.next()) {
		%>
		<tr>
			<td><%=resultSet.getString("customerId")%></td>
			<td><%=resultSet.getString("customerName")%></td>
			<td><%=resultSet.getString("sex")%></td>
			<td><%=resultSet.getString("occupation")%></td>
			<td><%=resultSet.getString("eduLevel")%></td>
			<td><%=resultSet.getString("address")%></td>
			
			<!-- Set the id of button to the customerId, by this way, I can pass the customerId to the servlet, and easily update the person whose id is 'customerId' -->
			<td align="center">
				<!-- Two buttons, click one to redirect the modifyperson.jsp to update the people whose id is 'customerId', 
				click another to redirect the servlet to delete the person whose id is 'customerId' -->
				<input type="button"
				id=<%=resultSet.getString("customerId")%> value="修改" name="modify"
				onclick="passIdToJsp(this.id,this.name)"> 
				<input type="button" 
				id=<%=resultSet.getString("customerId")%> value="删除"
				name="delete" onclick="passIdToServlet(this.id,this.name)"></td>
		</tr>
		<%
			}
		%>
	</table>
	<button
		onclick="location.href='http://localhost:8888/Experiment1/addperson.jsp'">添加</button>


	<script>
		//pass the customer's id and button's name to the servlet.
		function passIdToServlet(but_id, but_name) {
			var del = confirm("确认删除吗？");
			if(del){
				window.location.href = "ProcessInformation1?id=" + but_id + "&name="
				+ but_name;
			}

		}
	
		//pass the custome's id to the jsp to modify.
		function passIdToJsp(but_id) {
			window.location.href = "modifyperson.jsp?id=" + but_id;
		}
	</script>
</body>
</html>