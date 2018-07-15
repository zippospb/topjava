package ru.javawebinar.topjava.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.bridge.SLF4JBridgeHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.jdbc.SqlConfig;
import org.springframework.test.context.junit4.SpringRunner;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.util.exception.NotFoundException;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.MealTestData.assertMatch;
import static ru.javawebinar.topjava.UserTestData.ADMIN_ID;
import static ru.javawebinar.topjava.UserTestData.USER_ID;


@ContextConfiguration({
        "classpath:spring/spring-app.xml",
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
        Meal expectedMeal = MEALS.get(3);
        assertMatch(service.get(expectedMeal.getId(), USER_ID), expectedMeal);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundGet() {
        Meal expectedMeal = MEALS.get(3);
        assertMatch(service.get(expectedMeal.getId(), ADMIN_ID), expectedMeal);
    }

    @Test
    public void delete() {
        List<Meal> expected = new ArrayList<>(MealTestData.MEALS);
        int idToRemove = expected.remove(3).getId();
        service.delete(idToRemove, USER_ID);
        assertMatch(service.getAll(USER_ID), expected);
    }

    @Test(expected = NotFoundException.class)
    public void notFoundDelete() {
        List<Meal> expected = new ArrayList<>(MealTestData.MEALS);
        int idToRemove = expected.remove(3).getId();
        service.delete(idToRemove, ADMIN_ID);
    }

    @Test
    public void getBetweenDates() {
        assertMatch(service.getBetweenDates(LocalDate.of(2015, 5, 30),
                LocalDate.of(2015, 5, 31), USER_ID), MEALS);
        assertMatch(service.getBetweenDates(LocalDate.MIN, LocalDate.of(2015, 5, 30),
                USER_ID), MEALS.subList(3, MEALS.size()));
        assertMatch(service.getBetweenDates(LocalDate.of(2015, 5, 31),
                LocalDate.MAX, USER_ID), MEALS.subList(0, 3));
        assertMatch(service.getBetweenDates(LocalDate.of(2018, 5, 31),
                LocalDate.of(2018, 6, 30), USER_ID), new ArrayList<>());
    }

    @Test
    public void getBetweenDateTimes() {
        assertMatch(service.getBetweenDateTimes(LocalDateTime.MIN, LocalDateTime.MAX, USER_ID), MEALS);
        assertMatch(service.getBetweenDateTimes(LocalDateTime.MIN,
                LocalDateTime.of(2015, 5, 30, 13, 0), USER_ID), MEALS.subList(4, MEALS.size()));
        assertMatch(service.getBetweenDateTimes(LocalDateTime.of(2015, 5, 31, 13, 0),
                LocalDateTime.MAX, USER_ID), MEALS.subList(0, 2));
    }

    @Test
    public void getAll() {
        assertMatch(service.getAll(USER_ID), MEALS);
    }

    @Test
    public void update() {
        List<Meal> expected = new ArrayList<>(MEALS);
        Meal oldMeal = expected.get(3);
        Meal newMeal = new Meal(oldMeal.getId(), oldMeal.getDateTime(), oldMeal.getDescription(), 5000);
        expected.set(3, newMeal);
        service.update(newMeal, USER_ID);
        assertMatch(expected, service.getAll(USER_ID));
    }

    @Test(expected = NotFoundException.class)
    public void notFoundUpdate() {
        List<Meal> expected = new ArrayList<>(MEALS);
        Meal oldMeal = expected.get(3);
        Meal newMeal = new Meal(oldMeal.getId(), oldMeal.getDateTime(), oldMeal.getDescription(), 5000);
        expected.set(3, newMeal);
        service.update(newMeal, ADMIN_ID);
    }

    @Test
    public void create() {
        Meal newMeal = new Meal(LocalDateTime.now(), "Дожорчик", 5000);
        service.create(newMeal, USER_ID);
        List<Meal> expected = new ArrayList<>(MEALS);
        expected.add(newMeal);
        expected.sort(Comparator.comparing(Meal::getDateTime).reversed().thenComparing(AbstractBaseEntity::getId));
        assertMatch(expected, service.getAll(USER_ID));
    }

    @Test(expected = Exception.class)
    public void createNonUniqueIndex(){
        Meal newMeal = new Meal(LocalDateTime.of(2015, 5, 31, 13, 0), "", 0);
        service.create(newMeal, USER_ID);
    }

    @Test
    public void createNonUniqueDateButUniqueIndex() {
        Meal newMeal = new Meal(LocalDateTime.of(2015, 5, 31, 13, 0), "", 0);
        service.create(newMeal, ADMIN_ID);
    }
}