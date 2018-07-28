package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.junit.Test;
import org.springframework.test.context.ActiveProfiles;
import ru.javawebinar.topjava.MealTestData;
import ru.javawebinar.topjava.UserTestData;
import ru.javawebinar.topjava.model.Meal;

import static ru.javawebinar.topjava.MealTestData.*;
import static ru.javawebinar.topjava.UserTestData.*;

@ActiveProfiles(profiles = {"datajpa"})
public class DataJpaMealServiceTest extends AbstractMealServiceTest{
    @BeforeClass
    public static void addTitleToLog(){
        AbstractMealServiceTest.addTitleToLog(DataJpaMealServiceTest.class.getSimpleName());
    }

    @Test
    public void getWithUser(){
        Meal actual = service.getWithUser(MEAL1_ID, USER_ID);
        MealTestData.assertMatch(actual, MEAL1);
        UserTestData.assertMatch(actual.getUser(), USER);
    }
}