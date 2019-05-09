<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta http-equiv="X-UA-Compatible" content="IE=edge">
	<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
	<meta name="description" content="">
	<meta name="author" content="">
	
	<title></title>
	
	<!-- monthpicker style : datepicker와 동일--> 
	<link href="jquery-ui.min.css" rel="stylesheet">
</head>
<body>
	<form action="" class="form-horizontal">
		<div class="form-group">
			<label class="control-label">날짜:&nbsp;&nbsp;</label>
			<input type="text" name="month" id="monthpicker">&nbsp;&nbsp;
			<input class="btn btn-primary btn-sm" type="submit" value="검색">
		</div>
	</form>
 
	<!-- Bootstrap core JavaScript-->
	<script src="vendor/jquery/jquery.min.js"></script>
	<script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>
 
	<!-- 날짜검색기능 -->
	<script src="jquery-ui.min.js"></script>
	<script src="jquery.mtz.monthpicker.js"></script>

	<script>
	    /* MonthPicker 옵션 */
	    var currentYear = (new Date()).getFullYear();
	    var startYear = currentYear-5;
	
	    var options = {
	            startYear: startYear,
	            finalYear: currentYear,
	            pattern: 'yyyy-mm',
	            monthNames: ['1월','2월','3월','4월','5월','6월','7월','8월','9월','10월','11월','12월']
	    };
	
			/* MonthPicker Set */
			$('#monthpicker').monthpicker(options);
	
			/* 버튼 클릭시 MonthPicker Show */
			$('#btn_monthpicker').bind('click', function () {
				$('#monthpicker').monthpicker('show');
		});
	</script>
</body>
</html>
