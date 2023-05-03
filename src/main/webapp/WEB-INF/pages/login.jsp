<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>登入頁面</title>
</head>
<body>
	<fieldset>
		<legend>登入作業</legend>
		<form method="post">
			<div>帳號:</div>
			<input type="text" name="username">
			<br/>
			<div>密碼:</div>
			<input type="password" name="password">
			<br/>
			<input type="submit" value="登入"/>
		</form>
	</fieldset>
	<h3>${message}</h3>
	<br/>
	<button onclick="location.href='register'">前往註冊頁面</button>
	
	
</body>
</html>