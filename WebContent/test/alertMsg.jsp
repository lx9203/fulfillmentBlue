<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>AlertMessage</title>
</head>
<body>
<script type="text/javascript">
	var message = '${requestScope.message}';
	var returnUrl = '${requestScope.url}';
	alert(message);
	document.location.href = returnUrl;
</script>

</body>
</html>