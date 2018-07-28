package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.model.User;

import static ru.javawebinar.topjava.MealTestData.MEALS;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {"datajpa"})
public class DataJpaUserServiceTest extends AbstractUserServiceTest {

    @BeforeClass
    public static void addTitleToLog() {
        AbstractUserServiceTest.addTitleToLog(DataJpaUserServiceTest.class.getSimpleName());
    }

    @Test
    public void getWithMeals() {
        User actual = service.getWithMeals(USER_ID);
        assertMatch(actual, USER);
        MealTestData.assertMatch(actual.getMeals(), MEALS);
    }
}