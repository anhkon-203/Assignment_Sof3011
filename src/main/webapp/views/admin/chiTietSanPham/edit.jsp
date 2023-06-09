<%--
  Created by IntelliJ IDEA.
  User: anhkon
  Date: 3/9/2023
  Time: 9:29 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="jakarta.tags.core" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/Assignment_Sof3011_war_exploded/css/bootstrap.min.css">
</head>
<body>

<div class="col-8 offset-2">
    <h1>Update Chi tiết sản phẩm</h1>
    <c:if test="${not empty sessionScope.mess_error}">
        <div class="alert alert-danger" role="alert">
                ${sessionScope.mess_error}
        </div>
        <% session.removeAttribute("mess_error"); %>
    </c:if>
    <form method="POST"
          action="/Assignment_Sof3011_war_exploded/chi-tiet-san-pham/update?id=${chiTietSp.id}">
        <div class="row">
            <div class=" col-md-4 ">
                <label  class="form-label">Năm bảo hành</label>
                <input type="number" class="form-control"  name="namBaoHanh" value="${chiTietSp.namBaoHanh}" >
            </div>

            <div class=" col-md-4 mb-3">
                <label  class="form-label">Số lượng tồn</label>
                <input type="number" class="form-control"  name="soLuongTon"value="${chiTietSp.soLuongTon}"  required>
            </div>
            <div class="col-md-4 mb-3">
                <label  class="form-label">Dòng Sản phẩm</label>
                <select class="form-control" name="idDong" required>
                    <c:forEach items="${listDongSp}" var="dongSp">
                        <option value="${dongSp.id}" ${dongSp.id == idDongsp ? "selected" : ""} >${dongSp.ten}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 mb-3">
                <label class="form-label">Giá nhập</label>
                <input type="number" class="form-control"   name="giaNhap"  value="${chiTietSp.giaNhap}"  required>
            </div>
            <div class="col-md-4 mb-3">
                <label for="nsx" class="form-label">Nhà sản xuất</label>
                <select class="form-control" id="nsx" name="idNSX" required>
                    <c:forEach items="${listNSX}" var="nsx">
                        <option value="${nsx.id}"  ${nsx.id == idNSX ? "selected" : ""}>${nsx.ten}</option>
                    </c:forEach>
                </select>
            </div>
            <div class="col-md-4 mb-3">
                <label class="form-label">Sản phẩm</label>
                <select class="form-control" name="idSp" required>
                    <c:forEach  items="${listSanPham}" var="sanPham">
                        <option value="${sanPham.id}"  ${sanPham.id == idSp ? "selected" : ""} >${sanPham.ten}</option>
                    </c:forEach>
                </select>
            </div>
        </div>
        <div class="row">
            <div class="col-md-4 mb-3">
                <label  class="form-label">Giá bán</label>
                <input type="number" class="form-control" name="giaBan"  value="${chiTietSp.giaBan}"  required>
            </div>
            <div class="col-md-4 mb-3">
                <label  class="form-label">Màu sắc</label>
                <select class="form-control"  name="idMauSac" required>
                    <c:forEach items="${listMauSac}" var="mauSac">
                        <option value="${mauSac.id}"  ${mauSac.id == idMauSac ? "selected" : ""} >${mauSac.ten}</option>
                    </c:forEach>
                </select>
            </div>
            <div class=" col-md-4 mb-3">
                <label  class="form-label">Mô tả</label>
                <input type="text" class="form-control"  name="moTa"  value="${chiTietSp.moTa}"  rows="3"></input>
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-6">
                <button class="btn btn-primary">Update</button>
            </div>
            <div class="col-6"></div>
        </div>
    </form>
</div>

</body>
</html>
