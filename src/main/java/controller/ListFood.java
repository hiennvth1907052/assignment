package controller;


import service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/food/list")
public class ListFood extends HttpServlet {
    private FoodService foodService = new FoodService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            req.setAttribute("listFood", foodService.getList());
            req.getRequestDispatcher("/food/list.jsp").forward(req, resp);
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

    }
}
