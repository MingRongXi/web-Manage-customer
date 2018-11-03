<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<title>Welcome</title>
</head>
<body>
	<div align="center">
		<h1>欢迎进入</h1>
		<br>
		<script>
			//document.write("<span id=time></span>")
			function a() {
				document.getElementById("time").innerText = new Date()
						.toLocaleString();
				setInterval(a, 1000);
			}
			window.onload = a
		</script>
		<form name="enter" id="enter" action="ProcessInformation1?method=enter"
			method="post">
			<span id=time></span> <input type="submit" value="进入">
		</form>
	</div>
</body>
</html>