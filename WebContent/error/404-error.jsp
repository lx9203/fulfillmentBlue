<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8" isErrorPage="true"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>ch14:404-error.jsp</title>
</head>
<jsp:useBean id="now" class="java.util.Date" />
<body>
<div align=center>
<H2>ch14:404-error.jsp[web.xml 지정]</H2>
<HR>

<table>
<tr width=100% bgcolor="pink"><td>
요청하신 파일을 찾을수 없습니다.<BR>
URL 주소를 다시한번 확인해 주세요!!.
</td></tr>
<tr><td>
${now}<p>
요청 실패 URI : ${pageContext.errorData.requestURI}<BR>
상태코드 : ${pageContext.errorData.statusCode}<BR>
</td></tr>
</table>
</div>
</html>