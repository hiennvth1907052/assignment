<%@ page import="entity.Food" %>
<%@ page import="java.util.ArrayList" %>
<%@ page import="entity.Category" %>
<%@ page import="java.util.HashMap" %>
<%@ page import="helper.ValidateConstant" %><%--
  Created by IntelliJ IDEA.
  User: ADMIN
  Date: 5/24/2021
  Time: 6:18 PM
  To change this template use File | Settings | File Templates.
--%>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%
    ArrayList<Category> catgories = (ArrayList<Category>) request.getAttribute("categories");
    HashMap<String, ArrayList<String>> errors = (HashMap<String, ArrayList<String>>) request.getAttribute("errors");
    if (errors == null){
        errors = new HashMap<>();
    }
%>
<html>
<head>
    <title>Tạo món ăn mới</title>
    <link rel="stylesheet"
          href="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/css/bootstrap.min.css"
          integrity="sha384-Gn5384xqQ1aoWXA+058RXPxPg6fy4IWvTNh0E263XmFcJlSAwiGgFAW/dAiS6JXm"
          crossorigin="anonymous">
</head>
<body>
<nav class="navbar navbar-expand-lg navbar-light bg-secondary">
    <a class="navbar-brand" href="#">
        <img src="../asset/logorestaurant.jpg" width="50" height="50" class="d-inline-block align-top" alt="restaurant">
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
        <h4 class="text-center pt-3">Tạo món ăn mới</h4>
        <form action="/food/create" method="post">
            <div class="form-group">
                <label>Tên món ăn</label>
                <input type="text" name="name" class="form-control" placeholder="Nhập tên món ăn" autocomplete="off">
                <small class="form-text text-muted">
                    <ul>
                        <%if (errors.containsKey(ValidateConstant.FOOD_FIELNAME_NAME)){
                            ArrayList<String> nameErrors = errors.get(ValidateConstant.FOOD_FIELNAME_NAME);
                            for (String msg:
                                    nameErrors){
                        %>
                        <li><%= msg%></li>
                        <%
                                }
                            }%>
                    </ul>
                </small>
            </div>
            <div class="form-group">
                <label>Loại món ăn</label>
                <select class="form-control" name="categoriesId">
                    <% if (catgories != null && catgories.size() > 0) {
                        for (Category cate:
                                catgories) {
                    %>
                    <option value="<%= cate.getId()%>"><%= cate.getName()%></option>
                    <%
                            }
                        }%>
                </select>
            </div>
            <div class="form-group">
                <label>Giá món ăn</label>
                <input type="number" name="price" min="0" class="form-control">
                <small class="form-text text-muted">
                    <ul>
                        <%if (errors.containsKey(ValidateConstant.FOOD_FIELNAME_PRICE)){
                            ArrayList<String> priceErrors = errors.get(ValidateConstant.FOOD_FIELNAME_PRICE);
                            for (String msgP:
                                    priceErrors){
                        %>
                        <li><%= msgP%></li>
                        <%
                                }
                            }%>
                    </ul>
                </small>
            </div>
            <div class="form-group">
                <label>Hình ảnh</label>
                <input type="text" name="avatar" class="form-control" placeholder="Nhập hình ảnh món ăn">
            </div>
            <div class="form-group">
                <label>Mô tả</label>
                <textarea type="text" name="descriptions" class="form-control" placeholder="Nhập mô tả" autocomplete="off" style="resize: vertical;"></textarea>
            </div>
            <input type="submit" class="btn btn-primary" value="Lưu">
        </form>
    </div>
    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js"
            integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl"
            crossorigin="anonymous"></script>
</body>
</html>
