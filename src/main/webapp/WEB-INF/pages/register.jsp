<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>註冊頁面</title>
</head>
<body>
	<fieldset>
		<legend>會員註冊</legend>
		<form method="post">
			<div>使用者名稱</div>
			<input type="text" name="userName"/>
			<div>使用者密碼</div>
			<input type="password" name="password"/>
			<div>真實姓名</div>
			<input type="text" name="realName"/>
			<div>EMAIL</div>
			<input type="text" name="email"/>
			<br/>
			<input type="submit" value="註冊"/>
		</form>
	</fieldset>
	<h3>${requestScope.message}</h3>
</body>
</html>