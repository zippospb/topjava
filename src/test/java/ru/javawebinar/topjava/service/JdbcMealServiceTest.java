package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"jdbc"})
public class JdbcMealServiceTest extends AbstractMealServiceTest{
    @BeforeClass
    public static void addTitleToLog(){
        AbstractMealServiceTest.addTitleToLog(JdbcMealServiceTest.class.getSimpleName());
    }
}