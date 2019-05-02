<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <meta name="viewport" content="width=device-width, initial-scale=1.0">
  <meta http-equiv="X-UA-Compatible" content="ie=edge">
  <title>ShopMain</title>
</head>
<body>
  <h2>ShopMain</h2>
  <p>${userName} 회원님이 로그인 하셨습니다. </p>
  <hr>
	<a href="UserProc?action=logout">로그아웃</a> <br><br>
  <div align=center>
	<table border="1" style="border-collapse:collapse;">
    <!-- 송장번호 표시방법? -->
  	<thead>
    	<tr>
        <th style="width:100px">상품명</th>
        <th style="width:100px">개수</th>
        <th style="width:100px">단가</th>
        <th style="width:100px">합계</th>
      </tr>
  	</thead>

  	<c:set var="productList" value="${requestScope.productList}"/>
  	<c:forEach var="product" items="${productList}">
    	<tr>
      	<td> ${product.iProductName} </td>
      	<td> ${product.iQuantity} </td>
      	<td> ${product.iProductPrice} </td>
        <td> ${product.iProductTotal} </td>

    	</tr>
  	</c:forEach>
    <tr>
      <td></td><td></td><td></td>
      <td>총액</td>
      <td>${requestScope.totalSum}</td>
    </tr>
  	</table>
    <input type ="button" value ="전체송장목록" name="B1">
    <input type ="submit" value ="송장보내기" name="B2">
  </div>

</body>
</html>
