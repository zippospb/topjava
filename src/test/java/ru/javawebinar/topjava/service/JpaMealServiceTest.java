package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"jpa"})
public class JpaMealServiceTest extends AbstractMealServiceTest{
    @BeforeClass
    public static void addTitleToLog(){
        AbstractMealServiceTest.addTitleToLog(JpaMealServiceTest.class.getSimpleName());
    }
}