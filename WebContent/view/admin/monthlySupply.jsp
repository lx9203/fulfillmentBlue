<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" uri="http://java.sun.com/jsp/jstl/fmt" %>
<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>월별 발주내역</title>

  <!-- Custom fonts for this template-->
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
  
  <link href="../css/jquery-ui.min.css" rel="stylesheet">
  
  <link href="../css/bluecompany.css" rel="stylesheet">
</head>
<body id="page-top">
  <!-- Page Wrapper -->
  <div id="wrapper">
    <c:set value="admin" var="navRecall"/>
    <%@ include file="../common/_navigator.jspf" %>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <%@ include file="../common/_top.jspf" %>
        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-2 text-gray-600 boldfont">월별 발주내역</h1>
            <form action="AdminProc?action=supplySearchMonthList" class="form-horizontal d-sm-inline-block" method="post">
			  <div class="form-group" style="margin-bottom:0">
			    <label class="control-label" style="margin-bottom:0">날짜:&nbsp;
			      <input type="text" name="month" id="monthpicker" class="picker-input">&nbsp;&nbsp;
			      <button class="btn btn-primary btn-sm shadow-sm" type="submit" value="검색">
			        <i class="fas fa-search"></i>&nbsp;&nbsp;검색
			      </button>
			    </label>
			  </div>
	        </form>
          </div>
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-sm-flex align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary d-sm-inline-block" style="line-height:2">${requestScope.selectMonth}</h6>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <colgroup>
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:33%">
					<col style="width:15%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:12%">
				  </colgroup>
                  <thead>
                    <tr>
                      <th style="text-align: center;">발주코드</th>
                      <th style="text-align: center;">상품코드</th>
                      <th style="text-align: center;">상품이름</th>
                      <th style="text-align: center;">날짜</th>
                      <th style="text-align: center;">발주량</th>
                      <th style="text-align: center;">단가</th>
                      <th style="text-align: center;">총액</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th colspan="5"></th>
                      <th style="text-align: center;">총액</th>
                      <th style="text-align: right;"><fmt:formatNumber value="${requestScope.supplyTotalSales}" pattern="#,##0"/>원</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <c:set var="supplyList" value ="${requestScope.supplyList}"/>
					<c:forEach var="supply" items="${supplyList}">
	                 <tr>
                      <td style="text-align: center;">${supply.sCode}</td>
                      <td style="text-align: center;">${supply.pCode}</td>
                      <td style="text-align: left;">${supply.pName}</td>
                      <td style="text-align: center;">${supply.sDate}</td>
                      <td style="text-align: right;">${supply.sQuantity}개</td>
                      <td style="text-align: right;"><fmt:formatNumber value="${supply.pPrice}" pattern="#,##0"/>원</td>
                      <td style="text-align: right;"><fmt:formatNumber value="${supply.sTotalPrice}" pattern="#,##0"/>원</td>
                   	</tr>
                 	</c:forEach>
                  </tbody>
                </table>
              </div>
            </div>
          </div>
          <!-- 위까지 그래프  -->
        </div>
        <!-- /.container-fluid -->
      </div>
      <!-- End of Main Content -->
      <%@ include file="../common/_bottom.jspf" %>
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
  <script src="../js/monthpicker.js"></script>
  <script src="../js/jquery.mtz.monthpicker.js"></script>
  
</body>
</html>
<%@ include file="../common/_messageModal.jspf" %>