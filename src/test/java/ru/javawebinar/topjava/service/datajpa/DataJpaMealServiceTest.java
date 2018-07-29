package ru.javawebinar.topjava.service.datajpa;

import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.service.AbstractMealServiceTest;

import static ru.javawebinar.topjava.MealTestData.MEAL1;
import static ru.javawebinar.topjava.MealTestData.MEAL1_ID;
import static ru.javawebinar.topjava.UserTestData.USER;
import static ru.javawebinar.topjava.UserTestData.USER_ID;

@ActiveProfiles(profiles = {"datajpa"})
public class DataJpaMealServiceTest extends AbstractMealServiceTest {

    @Test
    public void getWithUser(){
        Meal actual = service.getWithUser(MEAL1_ID, USER_ID);
        MealTestData.assertMatch(actual, MEAL1);
        UserTestData.assertMatch(actual.getUser(), USER);
    }
}