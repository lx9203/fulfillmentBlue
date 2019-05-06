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

  <title>상세페이지</title>

  <!-- Custom fonts for this template-->
  <link href="../../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../../css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="../../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
</head>
<body id="page-top">
  <!-- Page Wrapper -->
  <div id="wrapper">
    <%@ include file="_navigator.jspf" %>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <%@ include file="../common/_top.jspf" %>
        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-2 text-gray-800">상세페이지</h1>
            <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
            <i class="fas fa-download fa-sm text-white-50"></i> Generate Report
            </a>
          </div>
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3">
              <table>
                <tr>
                  <td><h6 class="m-0 font-weight-bold text-primary">송장번호: a1001&nbsp;&nbsp;</h6></td>
                  <td>&nbsp;&nbsp;Tiger Nixon&nbsp;&nbsp;</td>
                  <td>&nbsp;&nbsp;1&nbsp; Parliament&nbsp; Square,&nbsp; Royal&nbsp; Mile,&nbsp; Edinburgh&nbsp; EH1&nbsp; 1RE,&nbsp; UK&nbsp;&nbsp;</td>
                  <td>&nbsp;&nbsp;+44&nbsp;131&nbsp;226&nbsp;1414&nbsp;&nbsp;</td>
                </tr>
              </table>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>주문번호</th>
                      <th>상품명</th>
                      <th>개수</th>
                      <th>단가</th>
                      <th>합계</th>
                    </tr>
                  </thead>
                  <tfoot>
                    <tr>
                      <th></th>
                      <th></th>
                      <th></th>
                      <th>총액</th>
                      <th>34500</th>
                    </tr>
                  </tfoot>
                  <tbody>
                    <tr>
                      <td>a100022019050101</td>
                      <td>삼겹살 1kg</td>
                      <td>2</td>
                      <td>5000</td>
                      <td>10000</td>
                    </tr>
                    <tr>
                      <td>a100022019050102</td>
                      <td>목살 1kg</td>
                      <td>1</td>
                      <td>7000</td>
                      <td>7000</td>
                    </tr>
                    <tr>
                      <td>a100022019050103</td>
                      <td>대파 1단</td>
                      <td>1</td>
                      <td>3000</td>
                      <td>3000</td>
                    </tr>
                    <tr>
                      <td>a100022019050104</td>
                      <td>쌈장 150g</td>
                      <td>2</td>
                      <td>4000</td>
                      <td>8000</td>
                    </tr>
                    <tr>
                      <td>a100022019050105</td>
                      <td>젓가락 50개</td>
                      <td>1</td>
                      <td>1500</td>
                      <td>1500</td>
                    </tr>
                    <tr>
                      <td>a100022019050106</td>
                      <td>일회용용기 50개</td>
                      <td>1</td>
                      <td>2500</td>
                      <td>2500</td>
                    </tr>
                    <tr>
                      <td>a100022019050107</td>
                      <td>콜라 1.5L</td>
                      <td>1</td>
                      <td>2500</td>
                      <td>2500</td>
                    </tr>
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
  <script src="../../vendor/jquery/jquery.min.js"></script>
  <script src="../../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="../../vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="../../js/sb-admin-2.min.js"></script>

  <!-- Page level plugins -->
  <script src="../../vendor/datatables/jquery.dataTables.min.js"></script>
  <script src="../../vendor/datatables/dataTables.bootstrap4.min.js"></script>

  <!-- Page level custom scripts -->
  <script src="../../js/demo/datatables-demo.js"></script>
</body>
</html>
