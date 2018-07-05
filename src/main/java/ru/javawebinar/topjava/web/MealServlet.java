package ru.javawebinar.topjava.web;

import org.slf4j.Logger;
import ru.javawebinar.topjava.DAO.MealsDAO;
import ru.javawebinar.topjava.DAO.InMemoryMealsDAO;
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
    private MealsDAO dao;

    @Override
    public void init() {
        dao = new InMemoryMealsDAO();
    }

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        log.debug("redirect to meals");

        List<MealWithExceed> meals = MealsUtil.getFilteredWithExceeded(dao.findAll() ,LocalTime.MIN, LocalTime.MAX, 2000);
        req.setAttribute("meals", meals);
        req.getRequestDispatcher("/meals.jsp").forward(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");

        switch (req.getParameter("method")){
            case "update":
                update(req, resp, Integer.parseInt(req.getParameter("id")));
                return;
            case "save":
                save(req);
                break;
            case "delete":
                delete(Integer.parseInt(req.getParameter("id")));
                break;
            default:
                log.error("Illegal argument \"method\"");
        }

        resp.sendRedirect("meals");
    }

    private void delete(int id) {
        log.debug("delete meal with id " + id);

        dao.delete(id);
    }

    private void save(HttpServletRequest req) {
        LocalDateTime dateTime = LocalDateTime.parse(req.getParameter("dateTime"));
        String description = req.getParameter("description");
        int calories = Integer.parseInt(req.getParameter("calories"));
        String id = req.getParameter("id");

        Meal meal = new Meal(dateTime, description, calories);

        if(id.isEmpty()){
            log.debug("save new meal " + meal);

            dao.save(meal);
        } else {
            log.debug("update meal " + meal);

            dao.update(meal, Integer.parseInt(id));
        }
    }

    private void update(HttpServletRequest req, HttpServletResponse resp, int id) throws ServletException, IOException {
        log.debug("forward to mealEdit");

        Meal meal = dao.getById(id);
        req.setAttribute("meal", meal);
        req.getRequestDispatcher("/mealEdit.jsp").forward(req, resp);
    }
}
