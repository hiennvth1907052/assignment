package controller;

import entity.Category;
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
import java.util.HashMap;

@WebServlet(urlPatterns = "/food/edit")
public class EditFood extends HttpServlet {
    private FoodService foodService = new FoodService();
    private CategoriesService categoriesService = new CategoriesService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Food existFood = foodService.getById(id);
        if (existFood == null) {
            resp.sendRedirect("/food/list");
            return;
        }
        req.setAttribute("list", existFood);
        Category existCategory = categoriesService.getById(existFood.getCategoriesId());
        if (existCategory == null) {
            req.setAttribute("categories", "");
        } else {
            req.setAttribute("categories", existCategory);
        }
        req.setAttribute("errors", "");
        req.getRequestDispatcher("/food/edit.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        int id = Integer.parseInt(req.getParameter("id"));
        String code = req.getParameter("code");
        String name = req.getParameter("name");
        int categoriesId = Integer.parseInt(req.getParameter("categoriesId"));
        String descriptions = req.getParameter("descriptions");
        String avatar = req.getParameter("avatar");
        double price = Double.parseDouble(req.getParameter("price")) > 0 ? Double.parseDouble(req.getParameter("price")) : 0;
        int status = Integer.parseInt(req.getParameter("status"));
        Food exist = foodService.getById(id);
        if (exist == null){
            resp.sendRedirect("/food/list");
            return;
        }
        exist.setName(code);
        exist.setName(name);
        exist.setCategoriesId(categoriesId);
        exist.setDescriptions(descriptions);
        exist.setAvatar(avatar);
        exist.setPrice(price);
        exist.setStatus(status);
        boolean result = foodService.edit(id,exist);

        if (!result) {
            HashMap<String, ArrayList<String>> errors = exist.getErrors();
            Food existFood = foodService.getById(id);
            req.setAttribute("list", existFood);
            Category existCategory = categoriesService.getById(existFood.getCategoriesId());
            if (existCategory == null) {
                req.setAttribute("categories", "");
            } else {
                req.setAttribute("categories", existCategory);
            }
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/food/edit.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/food/list");
    }
}
