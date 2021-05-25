package controller;

import entity.Category;
import service.CategoriesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

@WebServlet(urlPatterns = "/categories/create")
public class CreateCategories extends HttpServlet {
    private CategoriesService categoriesService = new CategoriesService();
    private Category category = new Category();
    HashMap<String, ArrayList<String>> errors = category.getErrors();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("errors", errors);
        req.getRequestDispatcher("/categories/create.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        category.setName(req.getParameter("name"));
        boolean result = categoriesService.create(category);
        if (!result) {
            req.setAttribute("errors", errors);
            req.getRequestDispatcher("/categories/create.jsp").forward(req, resp);
            return;
        }
        resp.sendRedirect("/categories/list");
    }
}
