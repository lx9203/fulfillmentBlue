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
  <link href="../vendor/fontawesome-free/css/all.min.css" rel="stylesheet" type="text/css">
  <link href="https://fonts.googleapis.com/css?family=Nunito:200,200i,300,300i,400,400i,600,600i,700,700i,800,800i,900,900i" rel="stylesheet">

  <!-- Custom styles for this template-->
  <link href="../css/sb-admin-2.min.css" rel="stylesheet">
  
  <link href="../css/bluecompany.css" rel="stylesheet">
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

			<!-- item 1  -->
            <div class="col-lg-6 mb-4">
              <!-- 형상 틀 -->
              <div class="card shadow mb-4 fade-item center">
                <!-- 링크 -->
                <a class="card-link" href="ProductProc?action=meat">
                  <article style="height:0">
                    <div class="hover-fadeout" style="background-image: url('../img/meat/meat_main.jpg')"></div>
                    <div class="hover-fadein card shadow mb-4">
                      <h3>육류(Meat)</h3>
                      <button>Details</button>
			        </div>
                  </article>
                </a>
              </div>
            </div>
            
            <!-- item 2  -->
            <div class="col-lg-6 mb-4">
              <!-- 형상 틀 -->
              <div class="card shadow mb-4 fade-item center">
                <!-- 링크 -->
                <a class="card-link" href="ProductProc?action=seafood">
                  <article style="height:0">
                    <div class="hover-fadeout" style="background-image: url('../img/seafood/seafood_main.jpg')"></div>
                    <div class="hover-fadein card shadow mb-4">
                      <h3>해산물(Seafood)</h3>
                      <p>Lorem ipsum dolor amet, consectetur magna etiam elit. Etiam sed ultrices.</p>
                      <button>Details</button>
			        </div>
                  </article>
                </a>
              </div>
            </div>

          </div>

          <!-- Content Row 2-->
          <div class="row">
          
            <!-- item 1  -->
            <div class="col-lg-4 mb-4">
              <!-- 형상 틀 -->
              <div class="card shadow mb-4 fade-item center">
                <!-- 링크 -->
                <a class="card-link" href="ProductProc?action=BBQ">
                  <article style="height:0">
                    <div class="hover-fadeout" style="background-image: url('../img/BBQ/BBQ_main.jpg')"></div>
                    <div class="hover-fadein card shadow mb-4">
                      <h3>바베큐(BBQ)</h3>
                      <p>Lorem ipsum dolor amet, consectetur magna etiam elit. Etiam sed ultrices.</p>
                      <button>Details</button>
			        </div>
                  </article>
                </a>
              </div>
            </div>
            
            <!-- item 2  -->
            <div class="col-lg-4 mb-4">
              <!-- 형상 틀 -->
              <div class="card shadow mb-4 fade-item center">
                <!-- 링크 -->
                <a class="card-link" href="ProductProc?action=vegetable">
                  <article style="height:0">
                    <div class="hover-fadeout" style="background-image: url('../img/vegetable/vegetable_main.jpg')"></div>
                    <div class="hover-fadein card shadow mb-4">
                      <h3>야채/과일(Vegetable/Fruit)</h3>
                      <p>Lorem ipsum dolor amet, consectetur magna etiam elit. Etiam sed ultrices.</p>
                      <button>Details</button>
			        </div>
                  </article>
                </a>
              </div>
            </div>
            
            <!-- item 3 -->
            <div class="col-lg-4 mb-4">
              <!-- 형상 틀 -->
              <div class="card shadow mb-4 fade-item center">
                <!-- 링크 -->
                <a class="card-link" href="ProductProc?action=spicy">
                  <article style="height:0">
                    <div class="hover-fadeout" style="background-image: url('../img/spicy/spicy_main.jpg')"></div>
                    <div class="hover-fadein card shadow mb-4">
                      <h3>향신료(Spicy)</h3>
                      <p>Lorem ipsum dolor amet, consectetur magna etiam elit. Etiam sed ultrices.</p>
                      <button>Details</button>
			        </div>
                  </article>
                </a>
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
  <script src="../vendor/jquery/jquery.min.js"></script>
  <script src="../vendor/bootstrap/js/bootstrap.bundle.min.js"></script>

  <!-- Core plugin JavaScript-->
  <script src="../vendor/jquery-easing/jquery.easing.min.js"></script>

  <!-- Custom scripts for all pages-->
  <script src="../js/sb-admin-2.min.js"></script>

</body>
</html>

<%@ include file="../common/_messageModal.jspf" %>