<%@ page import="entity.Food" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/24/2021
  Time: 6:17 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Food> listFood = (ArrayList<Food>) request.getAttribute("listFood");
%>
<html>
<head>
    <title>Food list</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-secondary">
    <a class="navbar-brand" href="/food/list">
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
        <h4 class="text-left pt-3">Danh sách món ăn <a type="button" class="btn btn-sm btn-outline-success float-right" href="/food/create">Thêm mới</a></h4>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Mã món ăn</th>
                <th scope="col">Tên món ăn</th>
                <th scope="col">Loại món ăn</th>
                <th scope="col">Giá</th>
                <th scope="col">Ảnh</th>
                <th scope="col">Trạng thái</th>
                <th scope="col"></th>
            </tr>
            </thead>
            <tbody>
            <% if (listFood != null && listFood.size() > 0) {
                for (int i = 0; i < listFood.size(); i++) {
            %>
            <tr>
                <td><%= i%></td>
                <td><%= listFood.get(i).getId() %></td>
                <td><%= listFood.get(i).getName() %></td>
                <td><%= listFood.get(i).getCategoriesId() %></td>
                <td><%= listFood.get(i).getPrice() %></td>
                <td><img src="<%= listFood.get(i).getName() %>" alt="<%= listFood.get(i).getName() %>" width="40" height="40"></td>
                <td><%= listFood.get(i).getStatus() == 1 ? "Đang bán" : "Dừng bán" %></td>
                <td>
                    <a href="/food/edit?id=<%= listFood.get(i).getId() %>" class="btn btn-success">Sửa</a>
                    <a href="/food/delete?id=<%= listFood.get(i).getId() %>" class="btn btn-danger">Xóa</a>
                </td>
            </tr>
            <%
                    }
                }%>
            </tbody>
        </table>
    </div>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
