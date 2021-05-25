package controller;

import entity.Food;
import service.CategoriesService;
import service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;

@WebServlet(urlPatterns = "/food/create")
public class CreateFood extends HttpServlet {
    private FoodService foodService = new FoodService();
    private Food food = new Food();
    private CategoriesService categoriesService = new CategoriesService();
    HashMap<String, ArrayList<String>> errors = food.getErrors();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errors", errors);
        req.setAttribute("categories", categoriesService.getList());
        req.getRequestDispatcher("/food/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int cateId = 0;
        double parsePrice = 0;
        String name = req.getParameter("name");
        System.out.println(req.getParameter("categoriesId"));
        String categoriesId = req.getParameter("categoriesId");
        if (categoriesId != null && !categoriesId.trim().equals("")) {
            cateId = Integer.parseInt(categoriesId);
        }
        String descriptions = req.getParameter("descriptions");
        String avatar = req.getParameter("avatar");

        String price = req.getParameter("price");
        if (price != null && !price.trim().equals("")) {
            parsePrice = Double.parseDouble(price);
        }
        food.setName(name);
        food.setCategoriesId(cateId);
        food.setDescriptions(descriptions);
        food.setAvatar(avatar);
        food.setPrice(parsePrice);
        // validate
        if(!food.isValid()){
            req.setAttribute("errors", errors);
            req.setAttribute("categories", categoriesService.getList());
//            req.getRequestDispatcher("/food/create.jsp").forward(req, resp);
        }
        foodService.create(food);
        resp.sendRedirect("/food/list");
    }
}
