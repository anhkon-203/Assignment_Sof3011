<%--
  Created by IntelliJ IDEA.
  User: anhkon
  Date: 3/22/2023
  Time: 1:20 AM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
  <title>Product Detail</title>
  <link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/4.5.2/css/bootstrap.min.css">
  <link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/5.15.1/css/all.min.css" />
  <link rel="stylesheet" href="/css/style.css">
</head>
<body>
<div class="container">
  <div class="row">
    <div class="col-8 offset-2">
      <c:forEach var="ctsp" items="${sanPham}" varStatus="status">
        <div class="card mb-3">
          <img src="${ctsp.srcImage}" alt="ảnh sản phẩm" class="card-img-top img-fluid w-50">
          <div class="card-body">
            <h3 class="card-title">${ctsp.tenSp}</h3>
            <h3 class="card-text">$${ctsp.giaBan}</h3>
            <p class="card-text mau-sac">Màu sắc :${ctsp.tenMauSac}</p>
            <p class="card-text mo-ta">Mô tả: ${ctsp.moTa}</p>
            <form action="${pageContext.request.contextPath}/GioHangUserServlet/store?id=${ctsp.id}"
                  method="post">
              <div class="input-group mb-3">
                <div class="input-group-prepend">
                  <span class="input-group-text">Số lượng</span>
                </div>
                <input type="number" class="form-control" id="quantity-input" name="soLuong" min="1"
                       max="${ctsp.soLuongTon}">
                <p class="text-danger ml-3">còn ${ctsp.soLuongTon} sản phẩm</p>
              </div>
              <button type="submit" class="btn btn-primary">Thêm vào giỏ hàng</button>
            </form>
          </div>
        </div>
      </c:forEach>
    </div>
  </div>
</div>

  <!-- Related Products -->
  <h3 class="fw-bold mt-5 text-center">Các sản phẩm tương tự</h3>
  <div class="row mt-3">
    <div class="col-md-3 col-6">
      <div class="card">
        <img src="/Assignment_Sof3011_war_exploded/img/b1.jpg" class="card-img-top" alt="">
        <div class="card-body">
          <h5 class="card-title text-center">SP1</h5>
          <h6 class="card-subtitle mb-2 text-muted text-center">$30</h6>
        </div>
      </div>
    </div>
    <div class="col-md-3 col-6">
      <div class="card">
        <img src="/Assignment_Sof3011_war_exploded/img/b1.jpg" class="card-img-top" alt="">
        <div class="card-body">
          <h5 class="card-title text-center">SP2</h5>
          <h6 class="card-subtitle mb-2 text-muted text-center">$30</h6>
        </div>
      </div>
    </div>
    <div class="col-md-3 col-6">
      <div class="card">
        <img src="/Assignment_Sof3011_war_exploded/img/b1.jpg" class="card-img-top" alt="">
        <div class="card-body">
          <h5 class="card-title text-center">SP3</h5>
          <h6 class="card-subtitle mb-2 text-muted text-center">$30</h6>
        </div>
      </div>
    </div>
    <div class="col-md-3 col-6">
      <div class="card">
        <img src="/Assignment_Sof3011_war_exploded/img/b1.jpg" class="card-img-top" alt="">
        <div class="card-body">
          <h5 class="card-title text-center">SP4</h5>
          <h6 class="card-subtitle mb-2 text-muted text-center">$30</h6>
        </div>
      </div>
    </div>
  </div>
</div>

<script src="https://ajax.googleapis.com/ajax/libs/angularjs/1.8.2/angular.min.js"></script>
<script src="/js/app.js"></script>
<script src="/js/controllers/productDetailController.js"></script>

</body>
</html>