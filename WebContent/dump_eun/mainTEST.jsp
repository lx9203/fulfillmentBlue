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

  <title>관리자 메인페이지</title>

  <!-- Custom fonts for this template-->
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../css/sb-admin-2.min.css" rel="stylesheet">
  
</head>
<c:set value="0" var="userType"/>

<body id="page-top">
  <!-- Page Wrapper -->
  <div id="wrapper">
    <c:set value="admin" var="navRecall"/>
    <%@ include file="common/_navigator.jspf" %>
    <!-- Content Wrapper -->
    <div id="content-wrapper" class="d-flex flex-column">
      <!-- Main Content -->
      <div id="content">
        <%@ include file="common/_top.jspf" %>
        <!-- Begin Page Content -->
        <div class="container-fluid">

          <!-- Page Heading -->
          <div class="d-sm-flex align-items-center justify-content-between mb-4">
            <h1 class="h3 mb-0 text-gray-800">운영현황</h1>
          </div>

          <!-- Content Row 1-->
          <div class="row">
            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">이달 판매수익 (현재까지)</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.thisTotalSales}원</div>
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
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">전년도 판매수익</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.lastYearTotalSales}원</div>
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
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">올해 판매 수익</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.thisYearTotalSales}원</div>
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
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">출고대기 건수(현재)</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">20건</div>
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
          
          <div class="row">
            <!-- Earnings (Monthly) Card Example -->
            <div class="col-xl-3 col-md-6 mb-4">
              <div class="card border-left-primary shadow h-100 py-2">
                <div class="card-body">
                  <div class="row no-gutters align-items-center">
                    <div class="col mr-2">
                      <div class="text-xs font-weight-bold text-primary text-uppercase mb-1">쇼핑몰 청구 대금</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.shopTotalSales}원</div>
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
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">운송사 지불 대금</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.transTotalSales}원</div>
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
                      <div class="text-xs font-weight-bold text-warning text-uppercase mb-1">공급사 지불 대금</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">${requestScope.supplyTotalSales}원</div>
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
                      <div class="text-xs font-weight-bold text-success text-uppercase mb-1">발주 건수(현재)</div>
                      <div class="h5 mb-0 font-weight-bold text-gray-800">20건</div>
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

          <!-- Content Row 2-->
          <div class="row">
            <!-- Area Chart -->
            <div class="col-xl-12">
              <div class="card shadow mb-4">
                <!-- Card Header - Dropdown -->
                <div class="card-header py-3 d-flex flex-row align-items-center justify-content-between">
                  <h6 class="m-0 font-weight-bold text-primary">월별 판매수익</h6>
                  <div class="dropdown no-arrow">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
                      <i class="fas fa-ellipsis-v fa-sm fa-fw text-gray-400"></i>
                    </a>
                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
                      <div class="dropdown-header">Dropdown Header:</div>
                      <a class="dropdown-item" href="#">Action</a>
                      <a class="dropdown-item" href="#">Another action</a>
                      <div class="dropdown-divider"></div>
                      <a class="dropdown-item" href="#">Something else here</a>
                    </div>
                  </div>
                </div>
                <!-- Card Body -->
                <div class="card-body">
                  <div class="chart-area">
                  	<canvas id="monthTotalPrice"></canvas>
                  </div>
                </div>
              </div>
            </div>

          </div>
          <!-- end of contant row 2 -->
          
          <form>
          	<div class="form-group">
          		<div class='d-inline-block'>
				<select class="custom-select custom-select-sm no-arrow" name='category'>
					<option value='' selected>-- 선택 --</option>
					<option value='1'>육류</option>
					<option value='2'>해산물</option>
					<option value='3'>바베큐</option>
					<option value='4'>야채/과일</option>
					<option value='5'>향신료</option>
				</select>
				</div>
				<input class="btn btn-primary btn-sm shadow-sm" type="submit" value="검색">
			</div>
          </form>
          
          <form class="d-none d-sm-inline-block form-inline mr-auto ml-md-3 my-2 my-md-0 mw-100">
            <div class="input-group d-inline-block">
              
              <div class="dropdown no-arrow">
                    <a class="dropdown-toggle" href="#" role="button" id="dropdownMenuLink" data-toggle="dropdown" aria-haspopup="true" aria-expanded="false">
              			<input type="text" class="form-control bg-light border-0 small" placeholder="#" aria-label="Search" aria-describedby="basic-addon2">
                    </a>
               
                    <div class="dropdown-menu dropdown-menu-right shadow animated--fade-in" aria-labelledby="dropdownMenuLink">
                      <span class="dropdown-item">육류</span>
                      <span class="dropdown-item">해산물</span>
                      <span class="dropdown-item">바베큐</span>
                      <span class="dropdown-item">야채/과일</span>
                      <span class="dropdown-item">향신료</span>
                    </div>
                  </div>
                </div>
              
                <input class="btn btn-primary btn-sm shadow-sm" type="submit" value="검색">
          </form>
          <br>
          <hr>
        	<i class="fas fa-warehouse"></i><br>
        	<i class="fab fa-docker">  <b>창고</b></i><br>
        	<i class="fas fa-truck">  운송사</i><br>
        	<i class="fas fa-shipping-fast"></i><br>
        	
        	<i class="fas fa-gift">  쇼핑몰</i><br>
        	<i class="fas fa-gifts"></i><br>
        	<i class="fas fa-dolly-flatbed">  공급업체</i><br>
        	
        	<i class="fas fa-truck-loading"></i><br>
        	<i class="fas fa-dolly"></i><br>
        	<br>
        	
        	<i class="fas fa-door-open">  로그아웃</i><br>
        	
        	<hr>
        	네비게이션<br>
        	<i class="fas fa-chart-bar">  메인화면</i><br>
        	<i class="fas fa-poll"></i><br>
        	
        	<i class="fas fa-boxes"></i><br>
        	
          	<i class="fas fa-coins">  판매내역</i><br>
          	<br>
          	<i class="fas fa-truck">  운송내역</i><br>
          	<i class="fas fa-shipping-fast"></i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-file-export">  운송신청내역</i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-file-invoice"></i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-file-alt">  월별운송내역</i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-list-alt"></i><br>
          	<br>
          	<i class="fas fa-dolly-flatbed">  발주내역</i><br>
          	<i class="fas fa-dolly"></i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-file-export">  발주신청내역</i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-file-invoice"></i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-file-alt">  월별발주내역</i><br>
          	&nbsp;&nbsp;&nbsp;&nbsp;<i class="fas fa-list-alt"></i><br>
          	
          	<hr>
          	메인페이지<br>
          	<i class="fas fa-won-sign"></i><br>
          	
          	<hr>
          	<i class="fas fa-th-large">  카탈로그메인</i><br>
          	<i class="fas fa-th">  카탈로그 항목</i><br>
          	
          	<hr>
          	<i class="fas fa-tasks"></i>일별<br>
          	<i class="fas fa-list"></i>월별<br>
          <!-- Single button -->
<div class="btn-group">
  <input type="text" class="btn btn-default dropdown-toggle" data-toggle="dropdown" aria-expanded="false">
    
  <ul class="dropdown-menu" role="menu">
    <li>Action</li>
    <li>Another action</li>
    <li>Something else here</li>
    <li>Separated link</li>
  </ul>
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
  <script src="../vendor/chart.js/Chart.min.js"></script>

  <!-- Page level custom scripts -->
  <%@ include file="_comboChartBarLine.jspf" %>
  
  <c:set value="true" var="msgState"/>
  <c:set value="경고창 모달 팝업 작동확인" var="message"/>
</body>
</html>
<%@ include file="common/_messageModal.jspf" %>