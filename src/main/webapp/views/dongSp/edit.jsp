<%--
  Created by IntelliJ IDEA.
  User: anhkon
  Date: 3/11/2023
  Time: 4:26 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>Title</title>
    <link rel="stylesheet" href="/Assignment_Sof3011_war_exploded/css/bootstrap.min.css">
</head>
<body>
<div class="col-8 offset-2">
    <h1>Update Dòng sản phẩm</h1>
    <form method="POST"
          action="/Assignment_Sof3011_war_exploded/dong-san-pham/update?ma=${dongSp.ma}">
        <div class="row mt-3">
            <div class="col-6">
                <label>Mã</label>
                <input type="text" name="ma" class="form-control" value="${dongSp.ma}" disabled />
            </div>
            <div class="col-6">
                <label>Tên</label>
                <input type="text" name="ten" class="form-control" value="${dongSp.ten}" />
            </div>
        </div>
        <div class="row mt-3">
            <div class="col-6">
                <button class="btn btn-primary">Thêm mới</button>
            </div>
            <div class="col-6"></div>
        </div>

    </form>
</div>
</body>
</html>
