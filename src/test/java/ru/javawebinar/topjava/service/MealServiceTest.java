package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Collections;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app-jdbc.xml",
        "classpath:spring/spring-db.xml"
})
@Sql(scripts = {"classpath:db/initDB.sql","classpath:db/populateDB.sql"}, config = @SqlConfig(encoding = "UTF-8"))
@RunWith(SpringRunner.class)
public class MealServiceTest {

    static {
        SLF4JBridgeHandler.install();
    }

    @Autowired
    private MealService service;

    @Test
    public void get() {
        assertMatch(service.get(MEAL_ID, USER_ID), USER_MEAL);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        service.get(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void delete() {
        service.delete(MEAL_ID, USER_ID);
        assertMatch(service.getAll(USER_ID), USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        service.delete(MEAL_ID, ADMIN_ID);
    }

    @Test
    public void getBetweenDatesCheckUpperLimit() {
        assertMatch(service.getBetweenDates(LocalDate.MIN, LocalDate.of(2015, 5, 30),
                USER_ID), USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test
    public void getBetweenDatesCheckLowerLimit() {
        assertMatch(service.getBetweenDates(LocalDate.of(2015, 5, 31), LocalDate.MAX, USER_ID)
                , USER_MEAL, USER_MEAL2, USER_MEAL3);
    }

    @Test
    public void getBetweenDatesCheckNoLimit() {
        assertMatch(service.getBetweenDates(LocalDate.MIN, LocalDate.MAX, USER_ID)
                , USER_MEAL, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test
    public void getBetweenDatesCheckOutOfRange() {
        assertMatch(service.getBetweenDates(LocalDate.of(2018, 5, 31),
                LocalDate.of(2018, 6, 30), USER_ID), Collections.emptyList());
    }

    @Test
    public void getBetweenDateTimesCheckUpperLimit() {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.MIN,
                LocalDateTime.of(2015, 5, 30, 13, 0), USER_ID), USER_MEAL5, USER_MEAL6);
    }

    @Test
    public void getBetweenDateTimesCheckLowerLimit() {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 31, 13, 0),
                LocalDateTime.MAX, USER_ID), USER_MEAL, USER_MEAL2);
    }



    @Test
    public void getBetweenDateTimesNoLimits() {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.MIN, LocalDateTime.MAX, USER_ID),
                USER_MEAL, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), USER_MEAL, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test
    public void update() {
        Meal newMeal = new Meal(USER_MEAL);
        newMeal.setCalories(5000);
        service.update(newMeal, USER_ID);
        assertMatch(service.getAll(USER_ID), newMeal, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        service.update(USER_MEAL, ADMIN_ID);
    }

    @Test
    public void create() {
        service.create(NEW_MEAL, USER_ID);
        assertMatch(service.getAll(USER_ID), NEW_MEAL, USER_MEAL, USER_MEAL2, USER_MEAL3, USER_MEAL4, USER_MEAL5, USER_MEAL6);
    }

    @Test(expected = Exception.class)
    public void createNonUniqueIndex(){
        service.create(NOT_UNIQUE_DATE_MEAL, USER_ID);
    }

    @Test
    public void createNonUniqueDateButUniqueIndex() {
        service.create(NOT_UNIQUE_DATE_MEAL, ADMIN_ID);
    }
}