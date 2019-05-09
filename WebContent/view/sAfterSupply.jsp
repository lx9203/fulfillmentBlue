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

  <title>월별 납품내역</title>

  <!-- Custom fonts for this template-->
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../css/sb-admin-2.min.css" rel="stylesheet">

  <!-- Custom styles for this page -->
  <link href="../vendor/datatables/dataTables.bootstrap4.min.css" rel="stylesheet">
  
  <link href="../css/jquery-ui.min.css" rel="stylesheet">
</head>
<body id="page-top">
  <!-- Page Wrapper -->
  <div id="wrapper">
    <%@ include file="s_navigator.jspf" %>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <%@ include file="common/_top.jspf" %>
        <!-- Begin Page Content -->
        <div class="container-fluid">
          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-2 text-gray-800">월별 납품내역</h1>
            <form action="" class="form-horizontal d-sm-inline-block" method="post">
			  <div class="form-group" style="margin-bottom:0">
			    <label class="control-label" style="margin-bottom:0">날짜:&nbsp;
			      <input type="text" name="monthCustomer" id="monthpicker" style="border:1px sloid; border-radius:.2rem;">&nbsp;&nbsp;
			      <input class="btn btn-primary btn-sm shadow-sm" type="submit" value="검색">
			    </label>
			  </div>
	        </form>
          </div>
          <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-sm-flex align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary d-sm-inline-block" style="line-height:2">${selectDate}</h6>
              <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
                <i class="fas fa-download fa-sm text-white-50"></i> Generate Report
              </a>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <thead>
                    <tr>
                      <th>발주코드</th>
                      <th>회사명</th>
                      <th>날짜</th>
                      <th>금액</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td><a href="requestDetail.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td>a1002</td>
                      <td>Garrett Winters</td>
                      <td>2011/07/25</td>
                      <td>$170,750</td>
                    </tr>
                    <tr>
                      <td>a1003</td>
                      <td>Ashton Cox</td>
                      <td>2009/01/12</td>
                      <td>$86,000</td>
                    </tr>
                    <tr>
                      <td>a1004</td>
                      <td>Cedric Kelly</td>
                      <td>2012/03/29</td>
                      <td>$433,060</td>
                    </tr>
                    <tr>
                      <td>a1005</td>
                      <td>Airi Satou</td>
                      <td>2008/11/28</td>
                      <td>$162,700</td>
                    </tr>
                    <tr>
                      <td>a1006</td>
                      <td>Brielle Williamson</td>
                      <td>2012/12/02</td>
                      <td>$372,000</td>
                    </tr>
                    <tr>
                      <td>a1007</td>
                      <td>Herrod Chandler</td>
                      <td>2012/08/06</td>
                      <td>$137,500</td>
                    </tr>
                    <tr>
                      <td>a1008</td>
                      <td>Rhona Davidson</td>
                      <td>2010/10/14</td>
                      <td>$327,900</td>
                    </tr>
                    <tr>
                      <td>a1009</td>
                      <td>Colleen Hurst</td>
                      <td>2009/09/15</td>
                      <td>$205,500</td>
                    </tr>
                    <tr>
                      <td>a1010</td>
                      <td>Sonya Frost</td>
                      <td>2008/12/13</td>
                      <td>$103,600</td>
                    </tr>
                    <tr>
                      <td>a1011</td>
                      <td>Jena Gaines</td>
                      <td>2008/12/19</td>
                      <td>$90,560</td>
                    </tr>
                    <tr>
                      <td>a1012</td>
                      <td>Quinn Flynn</td>
                      <td>2013/03/03</td>
                      <td>$342,000</td>
                    </tr>
                    <tr>
                      <td>a1013</td>
                      <td>Charde Marshall</td>
                      <td>2008/10/16</td>
                      <td>$470,600</td>
                    </tr>
                    <tr>
                      <td>a1014</td>
                      <td>Haley Kennedy</td>
                      <td>2012/12/18</td>
                      <td>$313,500</td>
                    </tr>
                    <tr>
                      <td>a1015</td>
                      <td>Tatyana Fitzpatrick</td>
                      <td>2010/03/17</td>
                      <td>$385,750</td>
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
      <%@ include file="common/_bottom.jspf" %>
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
  <script src="../js/jquery.mtz.monthpicker.js"></script>
  
</body>
</html>
