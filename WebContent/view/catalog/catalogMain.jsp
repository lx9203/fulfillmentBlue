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

  <title>제품소개</title>

  <!-- Custom fonts for this template-->
  <link href="../../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../../css/sb-admin-2.min.css" rel="stylesheet">
  
  <style>
  .hover-fadeout {
    height: 16rem;
    background-image: url(../../img/meat/meat_main.jpg);
    background-position: center center;
    background-size: cover;
    border-radius: .35rem
  }
  
  .hover-fadeout:hover > .hover-fadeout {
	opacity:0.5;
  }
  
  .hover-fadeout:hover > .hover-fadein {
	opacity:1;
  }
  </style>

</head>

<body id="page-top">

  <!-- Page Wrapper -->
  <div id="wrapper">

    <c:set value="catalog" var="navRecall"/>
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
            <h1 class="h3 mb-0 text-gray-800">제품소개</h1>
          </div>

          <!-- Content Row 1-->
          <div class="row">


			<!-- 이미지 위에 글씨 올라갈수 있도록 바꾸기  -->
			
			
            <div class="col-lg-4 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4">
                <a class="card-link" href="cCatalogList.jsp">
	                <div class="card-header py-3">
	                  <h6 class="m-0 font-weight-bold text-primary">육류(Meet)</h6>
	                </div>
	                <div class="card-body">
	                  <div class="text-center">
	                    <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" style="height: 8rem;" src="../../img/meat/meat_main.jpg" alt="">
	                  </div>
	                </div>
                </a>
              </div>
            </div>

            <div class="col-lg-4 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">해산물(Seafood)</h6>
                </div>
                <div class="card-body">
                  <div class="text-center">
                    <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" style="height: 8rem;" src="../../img/seafood/seafood_main.jpg" alt="">
                  </div>
                </div>
              </div>
            </div>
            
            <div class="col-lg-4 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">바베큐(BBQ)</h6>
                </div>
                <div class="card-body">
                  <div class="text-center">
                    <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" style="height: 8rem;" src="../../img/BBQ/BBQ_main.jpg" alt="">
                  </div>
                </div>
              </div>
            </div>

          </div>

          <!-- Content Row 2-->
          <div class="row">

            <div class="col-lg-6 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">야채/과일(Vegetable/Fruit)</h6>
                </div>
                <div class="card-body">
                  <div class="text-center">
                    <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" src="../../img/vegetable/vegetable_main.jpg" alt="">
                  </div>
                </div>
              </div>
            </div>

            <div class="col-lg-6 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4">
                <div class="card-header py-3">
                  <h6 class="m-0 font-weight-bold text-primary">향신료(Spicy)</h6>
                </div>
                <div class="card-body">
                  <div class="text-center">
                    <img class="img-fluid px-3 px-sm-4 mt-3 mb-4" src="../../img/spicy/spicy_main.jpg" alt="">
                  </div>
                </div>
              </div>
            </div>
          </div>

		  <!-- test Row 1-->
          <div class="row">
			<!-- 이미지 위에 글씨 올라갈수 있도록 바꾸기  -->
            <div class="col-lg-4 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4 fade-item center" id="meet" style="height:16rem; position:unset"> <!-- 형상 틀 -->
                <article style="height:0">
                  <div class="hover-fadeout" style="position: relative;"></div>
                  <div class="hover-fadein" style="position: absolute; left:0px; top:0px;">
                    <h3>Title</h3>
                    <p>Lorem ipsum dolor amet, consectetur magna etiam elit. Etiam sed ultrices.</p>
                    <ul class="">
                      <li><input type="button">Details</span></li>
                    </ul>
			      </div>
                </article>
              </div>
            </div>
            
              <div class="col-lg-4 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4 fade-item center" id="meet" style="height:16rem; position:unset"> <!-- 형상 틀 -->
                <article style="height:0">
                  <div class="hover-fadeout" style="position: relative;"></div>
                  <div class="hover-fadein" style="position: absolute; left:0px; top:0px;">
                    <h3>Title</h3>
                    <p>Lorem ipsum dolor amet, consectetur magna etiam elit. Etiam sed ultrices.</p>
                    <ul class="">
                      <li><input type="button">Details</span></li>
                    </ul>
			      </div>
                </article>
              </div>
            </div>
          </div>
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
 
  <script>
  $(document).ready(function(e) {
	  $('.hover-fadein').hide();
	  $('.fade-item').hover(
		function(){
			$('.hover-fadeout').stop().fadeOut();
			$('.hover-fadein').stop().fadeIn();
		},
			
		function(){
			$('.hover-fadein').stop().fadeOut();
			$('.hover-fadeout').stop().fadeIn()
		}
  )});
  </script>
</body>
</html>