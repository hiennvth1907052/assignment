package controller;

import service.CategoriesService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/categories/list")
public class ListCategory extends HttpServlet {
    private CategoriesService categoriesService = new CategoriesService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setAttribute("listFood", categoriesService.getList());
        req.getRequestDispatcher("/categories/list.jsp").forward(req, resp);
    }

}
