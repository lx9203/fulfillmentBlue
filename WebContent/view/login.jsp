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

  <title>Blue Company</title>

  <!-- Custom fonts for this template-->
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../css/sb-admin-2.min.css" rel="stylesheet">

  <style>
    .blue-login {
	    background-image: url('http://www.klnews.co.kr/news/photo/201901/118631_26829_537.jpg');
	    background-size: cover;
	}
  </style>
</head>

<body class="bg-gradient-primary">
  <div class="container">
  <!-- Outer Row -->
    <div class="row justify-content-center">
      <div class="col-xl-10 col-lg-12 col-md-9">
        <div class="card o-hidden border-0 shadow-lg my-5">
          <div class="card-body p-0">
            <!-- Nested Row within Card Body -->
            <div class="row">
              <div class="col-lg-6 d-none d-lg-block blue-login"></div>
              <div class="col-lg-6">
                <div class="p-5">
                  <div class="text-center">
                    <h1 class="h4 text-gray-900 mb-4">Welcome Back!</h1>
                  </div>
                  <form action="UserProc?action=login" class="user" method="POST">
                   <!--  <div class="form-group" align=center style="line-height: 2">
                      <div class="custom-control custom-radio small d-inline-block">
                        <input type="radio" class="custom-control-input" name="userType" id="admin" value=0>
                        <label class="custom-control-label " for="admin">관리자&nbsp;</label>
                      </div>
                      <div class="custom-control custom-radio small d-inline-block">
                        <input type="radio" class="custom-control-input" name="userType" id="transport" value=1>
                        <label class="custom-control-label" for="transport">운송업체&nbsp;</label>
                      </div>
                      <div class="custom-control custom-radio small d-inline-block">
                        <input type="radio" class="custom-control-input" name="userType" id="shopping" value=2>
                        <label class="custom-control-label" for="shopping">쇼핑몰&nbsp;</label>
                      </div>
                      <div class="custom-control custom-radio small d-inline-block">
                        <input type="radio" class="custom-control-input" name="userType" id="seller" value=3>
                        <label class="custom-control-label" for="seller">공급사&nbsp;</label>
                      </div>
                    </div> -->

                    <div class="form-group">
                      <input type="text" class="form-control form-control-user" id="id" name="id" placeholder="아이디를 입력해 주세요">
                    </div>
                    <div class="form-group">
                      <input type="password" class="form-control form-control-user" id="password" name="password" placeholder="비밀번호를 입력해 주세요">
                    </div>

                    <div class="form-group">
                      <div class="custom-control custom-checkbox small">
                        <input type="checkbox" class="custom-control-input" id="customCheck">
                        <label class="custom-control-label" for="customCheck">Remember Me</label>
                      </div>
                    </div>
                    <input class="btn btn-primary btn-user btn-block" type="submit" value="로그인">
                 	<a href="catalogMain.jsp" class="btn btn-google btn-user btn-block">창고 둘러보기</a>
                  </form>
                  <hr>
<!-- 비밀번호 찾기 or 초기화 요청
                  <div class="text-center">
                    <a class="small" href="forgot-password.html">Forgot Password?</a>
                  </div>
-->
                  <div class="text-center">
                    <a class="small" href="register.jsp">Create an Account!</a>
                  </div>
                </div>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>
  </div>


  <!-- Bootstrap core JavaScript-->
  <script src="../vendor/jquery/jquery.min.js"></script>
  <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="../js/sb-admin-2.min.js"></script>
  
<!-- 변수 검사 -->
	<c:set var="msgState" value="${requestScope.msgState}"/>
	<c:if test="${msgState==true}" var="result">
		<script>
			$(function() {
				$('#loginerror').modal('show');
			});
		</script>
	</c:if>

</body>
</html>

<!-- Logout Modal-->
<div class="modal fade" id="loginerror" tabindex="-1" role="dialog" aria-labelledby="ModalLabel" aria-hidden="true">
  <div class="modal-dialog" role="document">
    <div class="modal-content">
      <div class="modal-header">
        <h5 class="modal-title" id="ModalLabel">Login Error!!</h5>
        <button class="close" type="button" data-dismiss="modal" aria-label="Close">
          <span aria-hidden="true">×</span>
        </button>
      </div>
      <div class="modal-body">${requestScope.message}</div>
      <div class="modal-footer">
        <a class="btn btn-primary" href="login.jsp">Cancel</a>
      </div>
    </div>
  </div>
</div>
