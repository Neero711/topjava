package ru.javawebinar.topjava.web;

import static org.slf4j.LoggerFactory.getLogger;
import java.io.IOException;
import java.time.LocalDateTime;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.MealTo;
import ru.javawebinar.topjava.util.MealsUtil;

public class MealServlet extends HttpServlet {

    private static final Logger log = getLogger(MealServlet.class);


    @Override
    protected void doGet(
        HttpServletRequest request, HttpServletResponse response)
        throws ServletException, IOException {
        String action = request.getParameter("action");

        if ("delete".equals(action)) {
            int id = Integer.parseInt(request.getParameter("id"));
            log.info("Delete meal with id={}", id);
            MealsUtil.getRepository().mealDelete(id);
            response.sendRedirect("meals");
            return;
        } else if ("create".equals(action) || "update".equals(action)) {
            final Meal meal = "create".equals(action) ?
                new Meal(LocalDateTime.now(), "", 1000) :
                MealsUtil.getRepository().getMealById(Integer.parseInt(request.getParameter("id")));
            request.setAttribute("meal", meal);
            request.getRequestDispatcher("/mealForm.jsp").forward(request, response);
            return;
        }

        log.debug("redirect to meals");


        List<MealTo> meals = MealsUtil.getAllWithExcess();

        request.setAttribute("meals", meals);
        //response.sendRedirect("meals.jsp");
        request.getRequestDispatcher("/meals.jsp").forward(request, response);
    }

    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
        throws IOException {
        request.setCharacterEncoding("UTF-8");

        String id = request.getParameter("id");
        String dateTime = request.getParameter("dateTime");
        String description = request.getParameter("description");
        String calories = request.getParameter("calories");

        Meal meal = new Meal(id.isEmpty() ? null : Integer.parseInt(id),
            LocalDateTime.parse(dateTime),
            description,
            Integer.parseInt(calories));

        log.info(meal.isNew() ? "Create {}" : "Update {}", meal);
        MealsUtil.getRepository().mealSave(meal);
        response.sendRedirect("meals");
    }
}
