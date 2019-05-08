<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>재고현황</title>

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
    <%@ include file="a_navigator.jspf" %>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <%@ include file="common/_top.jspf" %>
        <!-- Begin Page Content -->
        <div class="container-fluid">
        
          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">재고현황</h1>
<!-- 검색창 용도 미정           
            <form action="" class="form-horizontal d-sm-inline-block" method="post">
			  <div class="form-group" style="margin-bottom:0">
			    <label class="control-label" style="margin-bottom:0">날짜:&nbsp;
			      <input type="text" name="dateCustomer" id="datepicker1" style="border:1px sloid; border-radius:.2rem;">&nbsp;&nbsp;
			      <input class="btn btn-primary btn-sm shadow-sm" type="submit" value="검색">
			    </label>
			  </div>
	        </form>
--> 
          </div>
          
          <!-- Content Row 1-->
          <div class="row">
            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">판매건수</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">$40,000</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-calendar fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-success shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">운송건수</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">$215,000</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-dollar-sign fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

			<!-- Pending Requests Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-warning shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">출고대기 건수</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">18</div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-comments fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-info shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-info text-uppercase mb-1">판매대비 출고비율</div>
                      <div class="row no-gutters align-items-center">
                        <div class="col-auto">
                          <div class="h5 mb-0 mr-3 font-weight-bold text-gray-800">50%</div>
                        </div>
                        <div class="col">
                          <div class="progress progress-sm mr-2">
                            <div class="progress-bar bg-info" role="progressbar" style="width: 50%" aria-valuenow="50" aria-valuemin="0" aria-valuemax="100"></div>
                          </div>
                        </div>
                      </div>
                    </div>
                    <div class="col-auto">
                      <i class="fas fa-clipboard-list fa-2x text-gray-300"></i>
                    </div>
                  </div>
                </div>
              </div>
            </div>

          </div>
          <!-- end of Content Row 1-->
          
         <!-- DataTales Example -->
          <div class="card shadow mb-4">
            <div class="card-header py-3 d-sm-flex align-items-center justify-content-between">
              <h6 class="m-0 font-weight-bold text-primary d-sm-inline-block" style="line-height:2">[검색한 날짜]</h6>
              <a href="#" class="d-none d-sm-inline-block btn btn-sm btn-primary shadow-sm">
                <i class="fas fa-download fa-sm text-white-50"></i> Generate Report
              </a>
            </div>
            <div class="card-body">
              <div class="table-responsive">
                <table class="table table-bordered" id="dataTable" width="100%" cellspacing="0">
                  <colgroup>
					<col style="width:15%">
					<col style="width:25%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:10%">
					<col style="width:20%">
				  </colgroup>
                  <thead>
                    <tr>
                      <th>상품코드</th>
                      <th>상품이름</th>
                      <th>재고량</th>
                      <th>출고대기</th>
                      <th>입고예정</th>
                      <th>발주코드</th>
                    </tr>
                  </thead>
                  <tbody>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                    </tr>
                    <tr>
                      <td><a href="tDetailList.jsp" >a1001</a></td>
                      <td>Tiger Nixon</td>
                      <td>2011/04/25</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
                      <td>$320,800</td>
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
	
</body>
</html>