<%@ page import="entity.Food" %>
<%@ page import="entity.Category" %>
<%@ page import="java.util.ArrayList" %>
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
    Food food = (Food) request.getAttribute("list");
    ArrayList<Category> catgories = (ArrayList<Category>) request.getAttribute("categories");
    HashMap<String, ArrayList<String>> errors = (HashMap<String, ArrayList<String>>) request.getAttribute("errors");
    if (food == null){
        food = new Food();
    }
    if (errors == null){
        errors = new HashMap<>();
    }
%>
<html>
<head>
    <title>Chi tiết món ăn</title>
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
        <h4 class="text-center pt-3">Chi tiết món ăn</h4>
        <form action="/food/edit" method="post">
            <input type="hidden" class="form-control" name="id" value="<%= food.getId() %>">
            <div class="form-group">
                <label>Tên món ăn</label>
                <input type="text" name="name" class="form-control" placeholder="Nhập tên món ăn" value="<%= food.getName()%>">
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
                <select class="form-control" name="categoriesId" >
                    <% if (catgories != null && catgories.size() > 0) {
                        for (Category cate:
                                catgories) {
                    %>
                    <option <%= cate.getId() == food.getCategoriesId() ? "selected" : ""%> value="<%= cate.getId()%>"><%= cate.getName()%></option>
                    <%
                            }
                        }%>
                </select>
            </div>
            <div class="form-group">
                <label>Giá món ăn</label>
                <input type="number" name="price" min="0" class="form-control" value="<%= food.getPrice()%>">
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
                <label>Ảnh</label>
                <input type="text" name="name" class="form-control" placeholder="Nhập ảnh món ăn" value="<%= food.getAvatar()%>">
            </div>
            <div class="form-group">
                <label>Trạng thái</label>
                <select class="form-control" name="status" value="<%= food.getCategoriesId()%>">
                    <%if(food.getStatus() == 1){
                    %>
                    <option selected value="1">Đang bán</option>
                    <option value="2">Dừng bán</option>
                    <option value="0">Xóa</option>
                    <%
                        }if (food.getStatus() == 2){
                    %>
                    <option value="1">Đang bán</option>
                    <option selected  value="2">Dừng bán</option>
                    <option value="0">Xóa</option>
                    <%
                        }
                    %>
                </select>
            </div>
            <div class="form-group">
                <label>Mô tả</label>
                <textarea type="text" name="descriptions" class="form-control" placeholder="Nhập mô tả" style="resize: vertical;" value="<%= food.getDescriptions()%>"></textarea>
            </div>
            <input type="submit" class="btn btn-primary" value="Lưu">
        </form>
    </div>

    <script src="https://maxcdn.bootstrapcdn.com/bootstrap/4.0.0/js/bootstrap.min.js" integrity="sha384-JZR6Spejh4U02d8jOt6vLEHfe/JQGiRRSQQxSfFWpi1MquVdAyjUar5+76PVCmYl" crossorigin="anonymous"></script>
</body>
</html>
