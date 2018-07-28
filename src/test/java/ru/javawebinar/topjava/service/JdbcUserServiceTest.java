package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"jdbc"})
public class JdbcUserServiceTest extends AbstractUserServiceTest{
    @BeforeClass
    public static void addTitleToLog(){
        AbstractUserServiceTest.addTitleToLog(JdbcUserServiceTest.class.getSimpleName());
    }
}