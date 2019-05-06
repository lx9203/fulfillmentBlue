<%@ page language="java" contentType="text/html; charset=UTF-8"
  pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html lang="ko">
<head>
  <meta http-equiv="X-UA-Compatible" content="IE=edge">
  <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
  <meta name="description" content="">
  <meta name="author" content="">

  <title>Blue Company</title>

  <!-- Custom fonts for this template-->
  <link href="vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="css/sb-admin-2.min.css" rel="stylesheet">

  <style>
  .blue-login {
  background-image: url('http://www.klnews.co.kr/news/photo/201901/118631_26829_537.jpg');
  background-size: cover;
  }
  </style>

  <script type="text/javascript">
    function check(login) {
      var kLogin = login.loginType.length;
      for(var i=0;i<kLogin;i++){
        if(login.loginType[i].checked == true){
          if(login.loginType[i].value == 1){
          document.location.href = "mall/mallMain.jsp";
          } else if(login.loginType[i].value == 2) {
          document.location.href = "transport/transMain.jsp";
          } else if(login.loginType[i].value == 3) {
          document.location.href = "supplier/supplierMain.jsp";
          }
        }
      }
    }
  </script>
</head>
<body class="bg-gradient-primary">
  <div class="container">
    <!-- Outer Row -->
    <div class="card o-hidden border-0 shadow-lg my-5">
      <div class="card-body p-0">
        <!-- Nested Row within Card Body -->
        <div class="row">
          <!-- 메인화면 사진 -->
          <div class="col-lg-6 d-none d-lg-block blue-login"></div>
          <!-- 로그인 폼 -->
          <div class="col-lg-6">
            <div class="p-5">
              <div class="text-center">
                <h1 class="h4 text-gray-900 mb-4">Blue Company</h1>
              </div>
              <form class="user" name="login">
                <div class="form-group">
                  <input type="text" class="form-control form-control-user" id="userId" placeholder="userId">
                </div>
                <div class="form-group">
                  <input type="password" class="form-control form-control-user" id="exampleInputPassword" placeholder="Password">
                </div>
                <div class="form-group" align = center>
                  <div class="col-3" style="display : inline-block">
                    <input type="radio" class="" name="loginType" value="1">
                    <a class="small">쇼핑몰</a>
                  </div>
                  <div class="col-3" style="display : inline-block">
                    <input type="radio" class="" name="loginType" value="2">
                    <a class="small">운송사</a>
                  </div>
                  <div class="col-3" style="display : inline-block">
                    <input type="radio" class="" name="loginType" value="3">
                    <a class="small">구매처</a>
                  </div>
                </div>
                <input type="button" value="login" onclick="check(this.form)" class="btn btn-primary btn-user btn-block" style="text-color:white">
                <a href="" class="btn btn-google btn-user btn-block">창고 둘러보기</a>
              </form>
              <hr>
              <div class="text-center">
                <a class="small" href="register.jsp">Create an Account!</a>
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
