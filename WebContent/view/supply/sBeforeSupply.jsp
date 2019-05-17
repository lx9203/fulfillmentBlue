<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge">
<meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
<meta name="description" content="">
<meta name="author" content="">

<title>공급처 수주내역</title>

<!-- Custom fonts for this template-->
<link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
<link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

<!-- Custom styles for this template-->
<link href="../css/sb-admin-2.min.css" rel="stylesheet">

<!-- Custom styles for this page -->
<link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body id="page-top">
	<!-- Page Wrapper -->
	<div id="wrapper">
		<c:set value="supply" var="navRecall" />
		<%@ include file="../common/_navigator.jspf"%>
		<!-- Content Wrapper -->
		<div id="content-wrapper" class="d-flex flex-column">
			<!-- Main Content -->
			<div id="content">
				<%@ include file="../common/_top.jspf"%>
				<!-- Begin Page Content -->
				<div class="container-fluid">
					<!-- Page Heading -->
					<div class="d-sm-flex align-items-center justify-content-between mb-4">
						<h1 class="h3 mb-2 text-gray-600 boldfont">수주내역</h1>
					</div>
					<!-- DataTales Example -->
					<div class="card shadow mb-4">
						<div
							class="card-header py-3 d-sm-flex align-items-center justify-content-between">
							<h6 class="m-0 font-weight-bold text-primary d-sm-inline-block" style="line-height: 2">${requestScope.curDate}</h6>
							<a href="SupplyProc?action=complete" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
								<i class="fas fa-truck-moving"></i>&nbsp;&nbsp; 납품
							</a>
						</div>
						<div class="card-body">
							<div class="table-responsive">
								<table class="table table-bordered" id="dataTable" width="100%"
									cellspacing="0">
									<colgroup>
										<col style="width: 10%">
										<col style="width: 30%">
										<col style="width: 15%">
										<col style="width: 10%">
										<col style="width: 10%">
										<col style="width: 10%">
										<col style="width: 15%">
									</colgroup>
									<thead>
										<tr>
											<th style="text-align: center;">발주코드</th>
											<th style="text-align: center;">제품명</th>
											<th style="text-align: center;">날짜</th>
											<th style="text-align: center;">제품수량</th>
											<th style="text-align: center;">제품가격</th>
											<th style="text-align: center;">청구금액</th>
											<th style="text-align: center;">상태</th>
										</tr>
									</thead>
									<tbody>
										<c:set var="supplyList" value="${requestScope.supplyList}" />
										<c:forEach var="supply" items="${supplyList}">
											<tr>
												<td style="text-align: center;">${supply.sCode}</td>
												<td style="text-align: left;">${supply.sProductName}</td>
												<td style="text-align: center;">${supply.sDate}</td>
												<td style="text-align: right;">${supply.sQuantity}개</td>
												<td style="text-align: right;">${supply.sProductPrice }원</td>
												<td style="text-align: right;">${supply.sTotalPrice }원</td>
												<td style="text-align: center;">${supply.sState}</td>
											</tr>
										</c:forEach>
									</tbody>
									<tfoot>
										<tr>
											<th colspan="4"></th>
											<th style="text-align: center;">총액</th>
											<th style="text-align: right;">${requestScope.supplyTotalPrice}원</th>
											<th></th>
										</tr>
									</tfoot>
								</table>
							</div>
						</div>
					</div>
					<!-- 위까지 그래프  -->
				</div>
				<!-- /.container-fluid -->
			</div>
			<!-- End of Main Content -->
			<%@ include file="../common/_bottom.jspf"%>
		</div>
		<!-- End of Content Wrapper -->
	</div>
	<!-- End of Page Wrapper -->

	<!-- Bootstrap core JavaScript-->
	<script src="../vendor/jquery/jquery.min.js"></script>
	<script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

	<!-- Core plugin JavaScript-->
	<script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

	<!-- Custom scripts for all pages-->
	<script src="../js/sb-admin-2.min.js"></script>

	<!-- Page level plugins -->
	<script src="../vendor/datatables/jquery.dataTables.min.js"></script>
	<script src="../vendor/datatables/dataTables.bootstrap4.min.js"></script>

	<!-- Page level custom scripts -->
	<script src="../js/demo/datatables-demo.js"></script>

	<!-- 날짜검색기능 -->
	<script src="../js/jquery-ui.min.js"></script>
	<script src="../js/blue_company.js"></script>
	<script src="../js/datepicker.js"></script>
</body>
</html>

<%@ include file="../common/_messageModal.jspf"%>