<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<%@ page import = "product.*" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
	<title>메인 페이지</title>
	<style>
		thead { background-color : #00FF00; color : #494D4A;}
		th {
			border: 2px solid #333333;
			padding : 5px;
		}
		td, th{ text-align: center; padding :3px; }
		
		
	</style>
</head>
<body><center>
	<h2>회원 명단</h2>
	<p>${userName} 회원님이 로그인 하셨습니다. </p>
	<hr>
	<a href="UserProc?action=logout">로그아웃</a> <br><br>
	<table border="1" style="border-collapse:collapse;">
	<thead>
	<tr><th style="width:200px">제품명</th><th style="width:100px">가격</th><th style="width:100px">재고량</th></tr>
	</thead>
	
	<c:set var="productList" value="${requestScope.productList}"/>
	<c:forEach var="product" items="${productList}">
	<tr>
	<td> ${product.pName} </td>
	<td> ${product.pPrice} </td>
	<td> ${product.pQuantity} </td>
	<td><button onclick="location.href='MemberProcServlet?action=update&id=${member.id}'">수정</button></td>
	<td><button onclick="location.href='MemberProcServlet?action=delete&id=${member.id}'">삭제</button></td></tr>
	
	</c:forEach>
	</table>
</center>
</body>
</html>