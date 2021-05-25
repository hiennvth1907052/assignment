package controller;

import entity.Food;
import service.FoodService;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(urlPatterns = "/food/delete")
public class DeleteFood extends HttpServlet {
    private FoodService foodService = new FoodService();
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        int id = Integer.parseInt(req.getParameter("id"));
        Food existFood = foodService.getById(id);
        if (existFood == null){
            resp.sendRedirect("/food/list");
            return;
        }
        foodService.delete(id, existFood);
        resp.sendRedirect("/food/list");
    }
}
