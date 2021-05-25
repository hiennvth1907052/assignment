<%@ page import="entity.Category" %>
<%@ page import="java.util.ArrayList" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/24/2021
  Time: 6:14 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Category> listCategory = (ArrayList<Category>) request.getAttribute("listFood");
%>
<html>
<head>
    <title>List category</title>
    <meta name="viewport" content="width=device-width, initial-scale=1, shrink-to-fit=no">
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
        <h4 class="text-left pt-3">Danh mục món ăn <a type="button" class="btn btn-sm btn-outline-success float-right" href="/categories/create">Thêm mới</a></h4>
        <table class="table table-striped table-hover">
            <thead>
            <tr>
                <th scope="col">#</th>
                <th scope="col">Mã danh mục</th>
                <th scope="col">Tên danh mục</th>
            </tr>
            </thead>
            <tbody>
            <% if (listCategory != null && listCategory.size() > 0) {
                for (int i = 0; i < listCategory.size(); i++) {
            %>
            <tr>
                <td><%= i%></td>
                <td><%= listCategory.get(i).getId() %></td>
                <td><%= listCategory.get(i).getName() %></td>
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
