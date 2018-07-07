package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

import static ru.javawebinar.topjava.util.ValidationUtil.assureIdConsistent;
import static ru.javawebinar.topjava.util.ValidationUtil.checkNew;

@Controller
public class MealRestController {
    private final Logger log = LoggerFactory.getLogger(getClass());

    private final MealService service;

    @Autowired
    public MealRestController(MealService service) {
        this.service = service;
    }

    public List<MealWithExceed> getAll(){
        log.info("getAll");
        return MealsUtil.getWithExceeded(
                service.getAll(LocalDate.MIN, LocalDate.MAX), SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getAll(LocalDate fromDate, LocalDate toDate,
                                       LocalTime fromTime, LocalTime toTime){
        log.info("getAll with filter");
        return MealsUtil.getFilteredWithExceeded(service.getAll(fromDate, toDate),
                MealsUtil.DEFAULT_CALORIES_PER_DAY, fromTime, toTime);
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(id);
    }

    public Meal create(Meal meal){
        log.info("create {}", meal);
        checkNew(meal);
        return service.create(meal);
    }

    public void delete(int id){
        log.info("delete {}", id);
        service.delete(id);
    }

    public void update(Meal meal, int id){
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(meal);
    }
}