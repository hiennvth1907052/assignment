<%@ page import="entity.Category" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="helper.ValidateConstant" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/24/2021
  Time: 6:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    HashMap<String, ArrayList<String>> errors = (HashMap<String, ArrayList<String>>) request.getAttribute("errors");
    if (errors == null){
        errors = new HashMap<>();
    }
%>
<html>
<head>
    <title>Tạo danh mục mới</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
</head>
<body>
    <nav class="navbar navbar-expand-lg navbar-light bg-secondary">
        <a class="navbar-brand" href="#">
            <img src="../asset/logorestaurant.jpg" width="30" height="30" class="d-inline-block align-top" alt="restaurant">
        </a>
        <button class="navbar-toggler" type="button" data-toggle="collapse" data-target="#navbarSupportedContent" aria-controls="navbarSupportedContent" aria-expanded="false" aria-label="Toggle navigation">
            <span class="navbar-toggler-icon"></span>
        </button>

        <div class="collapse navbar-collapse" id="navbarSupportedContent">
            <ul class="navbar-nav mr-auto">
                <li class="nav-item active">
                    <a class="nav-link text-white" href="/food/list">Trang chủ <span class="sr-only">(current)</span></a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="/food/list">Món ăn</a>
                </li>
                <li class="nav-item">
                    <a class="nav-link text-white" href="/categories/list">Danh mục</a>
                </li>
            </ul>
        </div>
    </nav>
    <div class="container">
        <h1 class="text-center">Tạo danh mục mới</h1>
        <form action="/categories/create" method="post">
            <div class="form-group">
                <label>Tên danh mục</label>
                <input type="text" name="name" class="form-control" placeholder="Nhập tên danh mục" autocomplete="off">
                <%if (errors.containsKey(ValidateConstant.CATEGORY_FIELNAME_NAME)){
                %>
                <small class="form-text text-muted"><%= errors.get(ValidateConstant.CATEGORY_MESSAGE_NAME_REQUIRED)%></small>
                <%
                    }%>
            </div>
            <input type="submit" class="btn btn-sm btn-outline-success" value="Lưu">
        </form>
    </div>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
