<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>

  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Register</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">
  
  <style>
  	.blue-register {
    	background-image: url('http://www.klnews.co.kr/news/photo/201901/118631_26829_537.jpg');
    	background-size: cover;
	}
  </style>

</head>

<body class="bg-gradient-primary">

  <div class="container">

    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <div class="col-lg-6 d-none d-lg-block blue-register"></div>
          <div class="col-lg-6">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">Create an Account</h1>
              </div>
              <form class="user">
				<div class="form-group">
                  <input type="text" class="form-control form-control-user" id="userId" placeholder="userId">
                </div>
                <div class="form-group">
                  <input type="text" class="form-control form-control-user" id="company" placeholder="company">
                </div>
                <div class="form-group row">
                  <div class="col-sm-6 mb-3 mb-sm-0">
                    <input type="password" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password">
                  </div>
                  <div class="col-sm-6">
                    <input type="password" class="form-control form-control-user" id="exampleRepeatPassword" placeholder="Repeat Password">
                  </div>
                </div>
                <div class="form-group" align = left>
                	<div class="col-3" style="display : inline-block; max-width:20%; padding-right:0">
               			<a class="small">회원유형:&nbsp;</a>
               		</div>
                  	<div class="col-3" style="display : inline-block; max-width:20%; padding-right:0">
	            		<input type="radio" class="" name="loginType" value="1">
	            		<a class="small">쇼핑몰</a>
                  	</div>
	                <div class="col-3" style="display : inline-block; max-width:20%; padding-right:0">
	                    <input type="radio" class="" name="loginType" value="2">
	                    <a class="small">운송사</a>
	                </div>
	                <div class="col-3" style="display : inline-block; max-width:20%; padding-right:0">
	                    <input type="radio" class="" name="loginType" value="3">
	                    <a class="small">구매처</a>
	                </div>
                </div>
                <div class="form-group" align = left>
                	<div class="col-3" style="display : inline-block; max-width:20%; padding-right:0">
                		<a class="small">지역:&nbsp;</a>
                	</div>
                  	<div class="col-3" style="display : inline-block; max-width:15%; padding-right:0">
	            		<input type="radio" class="" name="loginType" value="1">
	            		<a class="small">경기</a>
                  	</div>
	                <div class="col-3" style="display : inline-block; max-width:15%; padding-right:0">
	                    <input type="radio" class="" name="loginType" value="2">
	                    <a class="small">중부</a>
	                </div>
	                <div class="col-3" style="display : inline-block; max-width:15%; padding-right:0">
	                    <input type="radio" class="" name="loginType" value="3">
	                    <a class="small">영남</a>
	                </div>
	                <div class="col-3" style="display : inline-block; max-width:15%; padding-right:0">
	                    <input type="radio" class="" name="loginType" value="3">
	                    <a class="small">서부</a>
	                </div>
                </div>
                  
                <a href="index.jsp" class="btn btn-primary btn-user btn-block">
                  Register Account
                </a>
              </form>
              <hr>
<!-- 비밀번호 찾기 or 초기화 요청      
              <div class="text-center">
                <a class="small" href="forgot-password.jsp">Forgot Password?</a>
              </div>
-->
              <div class="text-center">
                <a class="small" href="../index.jsp">Already have an account? Login!</a>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

  </div>

  <!-- Bootstrap core JavaScript-->
  <script src="vendor/jquery/jquery.min.js"></script>
  <script src="vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="js/sb-admin-2.min.js"></script>

</body>

</html>