package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealsDAO;
import ru.javawebinar.topjava.DAO.RamMealsDAO;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.List;

import static org.slf4j.LoggerFactory.getLogger;

public class MealServlet extends HttpServlet {
    private static final Logger log = getLogger(MealServlet.class);
    private final MealsDAO dao = new RamMealsDAO();

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(dao.findAll() ,LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", meals);
        req.getRequestDispatcher("meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        int id;
        switch (req.getParameter("method")){
            case "update":
                id = Integer.parseInt(req.getParameter("id"));
                update(req, resp, id);
                break;
            case "delete":
                id = Integer.parseInt(req.getParameter("id"));
                delete(req, resp, id);
                break;
            case "create":
                create(req, resp);
                break;
            case "save":
                save(req, resp);
                break;
        }
    }

    private void delete(HttpServletRequest req, HttpServletResponse resp, int id) throws IOException {
        Meal meal = dao.getById(id);
        dao.delete(meal);
        resp.sendRedirect("meals");
    }

    private void create(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
    }

    private void save(HttpServletRequest req, HttpServletResponse resp) throws IOException{
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");

        Meal meal = new Meal(dateTime, description, calories);

        if(id == null || id.isEmpty()){
            dao.save(meal);
        } else {
            dao.update(meal, Integer.parseInt(id));
        }
        resp.sendRedirect("meals");
    }

    private void update(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
        Meal meal = dao.getById(id);
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("mealEdit.jsp").forward(req, resp);
    }


}
