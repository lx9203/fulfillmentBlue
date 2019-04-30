<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>로그인</title>
<style>
	span {
		font-size : 20px;
	}
</style>
</head>
<body>
	<center> <br>
	<h2>사용자 로그인</h2> <br>
	<hr>
	<form name ="loginForm" action="UserProc?action=login" method=post>
		<label><span>사용자 유형 : </span>
			<input type ="radio" name ="id_no" value=1 size="10" >운송 업체 
			<input type ="radio" name ="id_no" value=2 size="10" >쇼핑몰 운영자
			<input type ="radio" name ="id_no" value=3 size="10" >구매처 운영자</label> <br><br>
		<label><span>ID : </span>
			<input type ="text" name ="id" size="10"></label> <br><br>
		<label><span>Password : </span>
			<input type ="password" name ="password" size="10"></label> <br><br>
		<label><input type ="submit" value ="로그인" name="B1"></label>
	</form>
		&nbsp;&nbsp;
		<br><button onclick="location.href='register.html'">회원가입</button>
	</center>

</body>
</html>