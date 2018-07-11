package ru.javawebinar.topjava.web.meal;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.MealService;
import ru.javawebinar.topjava.to.MealWithExceed;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

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
        return MealsUtil.getWithExceeded(service.getAll(SecurityUtil.authUserId()),
                SecurityUtil.authUserCaloriesPerDay());
    }

    public List<MealWithExceed> getAllByDateTime(LocalDate fromDate, LocalDate toDate,
                                                 LocalTime fromTime, LocalTime toTime){
        log.info("getAll with filter");
        List<Meal> meals = service.getAllByDate(SecurityUtil.authUserId(),
                Optional.ofNullable(fromDate).orElse(LocalDate.MIN),
                Optional.ofNullable(toDate).orElse(LocalDate.MAX));
        return MealsUtil.getFilteredWithExceeded(meals, MealsUtil.DEFAULT_CALORIES_PER_DAY,
                Optional.ofNullable(fromTime).orElse(LocalTime.MIN),
                Optional.ofNullable(toTime).orElse(LocalTime.MAX));
    }

    public Meal get(int id){
        log.info("get {}", id);
        return service.get(SecurityUtil.authUserId(), id);
    }

    public void create(Meal meal){
        log.info("create {}", meal);
        checkNew(meal);
        service.create(SecurityUtil.authUserId(), meal);
    }

    public void delete(int id){
        log.info("delete {}", id);
        service.delete(SecurityUtil.authUserId(), id);
    }

    public void update(Meal meal, int id){
        log.info("update {} with id={}", meal, id);
        assureIdConsistent(meal, id);
        service.update(SecurityUtil.authUserId(), meal);
    }

    private boolean isAbsent(String parameter){
        return parameter == null || parameter.isEmpty();
    }
}