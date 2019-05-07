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

          <!-- row 1번-->
          <div class="row">
          
            <div class="col-lg-6 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4 small">
                <table>
                  <tr>
                    <td rowspan="2">
                      <div class="container" style="height:6rem">
                        <img class="img-fluid"  style="max-height:100%; max-width:unset; padding:1rem 0 1rem 0" src="../../img/undraw_posting_photo.svg" alt="">
                      </div>
                    </td>
                    <td>
                      <div class="card-header" style="padding:5px 20px 5px 20px; background-color:white">
                        <h6 class="m-0 font-weight-bold text-primary">Illustrations</h6>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <div class="card-body" style="padding:5px 20px 5px 20px">
                        <p style="margin-block-end:0">
                          Add some quality, svg illustrations to your project courtesy of unDraw,
                          a constantly updated collection of beautiful svg images that you can use
                          completely free and without attribution!
                        </p>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
            
            <div class="col-lg-6 mb-4">
              <!-- Illustrations -->
              <div class="card shadow mb-4 small">
                <table>
                  <tr>
                    <td rowspan="2">
                      <div class="container">
                        <img class="img-fluid px-3 px-sm-2 mt-3 mb-2" src="../../img/undraw_posting_photo.svg" alt="">
                      </div>
                    </td>
                    <td>
                      <div class="card-header" style="padding:5px 20px 5px 20px; background-color:white">
                        <h6 class="m-0 font-weight-bold text-primary">Illustrations</h6>
                      </div>
                    </td>
                  </tr>
                  <tr>
                    <td>
                      <div class="card-body" style="padding:5px 20px 5px 20px">
                        <p>
                          Add some quality, svg illustrations to your project courtesy of unDraw,
                          a constantly updated collection of beautiful svg images that you can use
                          completely free and without attribution!
                        </p>
                      </div>
                    </td>
                  </tr>
                </table>
              </div>
            </div>
            
          </div>
          <!-- row 1번 끝-->
          
          

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

</body>

</html>