<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" import="java.util.*"%>
<!-- JSTL 기능 라이브러리 : if문, for문을 제거 가능!(set,forEach 가 주로 사용) -->
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %> 
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>게시판</title>
<style>
	thead { background-color : #00FF00; color : #494D4A; width : 600px;}
	th {
		border: 2px solid #333333;
		padding : 10px;
	}
	td {
		text-align :center;
		border: 1px solid #333333;
		padding : 5px;	
	}
	.button {
		float : right;
	}
	.foot {
		border: 0;
	}
	
</style>
</head>
<body>
<center>
	<h2>게시판</h2>
	<p> ${memberName} 회원님이 로그인 하셨습니다. </p>
	<hr>
	<a href="/jspbook/login/MemberProcServlet?action=intoMain&page=1">메인화면</a> &nbsp;&nbsp;
	<a href="/jspbook/twitter/twitterServlet?action=login">트윗</a>&nbsp; &nbsp;
	<a href="/jspbook/login/FileServlet?action=bbs">다운로드</a>&nbsp; &nbsp;
	<a href="/jspbook/login/MemberProcServlet?action=logout">로그아웃</a> <br><br>
	<div>
	<table border="1" style="border-collapse:collapse;">
	<thead>
	<tr><th>게시번호</th><th style="width:60%">제목</th><th>글쓴이</th><th>작성날짜</th><th>액션</th></tr>
	</thead>
	<tbody>
	
	<c:set var="bmList" value="${requestScope.bmList}"/>
	<c:forEach var="bbs" items="${bmList}">
	<tr><td> ${bbs.id} </td> 
	<td><a href="BbsServlet?action=look&id=${bbs.id}">${bbs.title}</a></td>
	<td> ${bbs.name} </td>
	<td> ${bbs.date} </td>
	<td>
	<button onclick="location.href='BbsServlet?action=update&id=${bbs.id}&memberId=${bbs.memberId}'">수정</button>&nbsp;&nbsp;
	<button onclick="location.href='BbsServlet?action=update&id=${bbs.id}&memberId=${bbs.memberId}'">삭제</button></td></tr>
	</c:forEach>
	</tbody>
	
	<tfoot>
	<tr ><th class="foot"  colspan="5"><button class="button" onclick="location.href='bbsInsert.jsp'">작성</button></th></tr>
	<tr>
	<th colspan="5">

	현재 페이지는 ${page} 쪽 입니다.<br><br>
	
	<button onclick="location.href='BbsServlet?action=list&page=${requestScope.page-1}'">&lt;</button>
	<c:set var="listPages" value="${requestScope.pageList}"/>
	<c:forEach var="listPage" items="${listPages}">
		${listPage }
	</c:forEach>
	<button onclick="location.href='BbsServlet?action=list&page=${requestScope.page+1}'">&gt;</button>
	</th>
	</tr>
	</tfoot>
	</table>
	</div>
	<br>
</center>
</body>
</html>