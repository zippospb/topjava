package ru.javawebinar.topjava.service;

import org.junit.BeforeClass;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles(profiles = {"jpa"})
public class JpaUserServiceTest extends AbstractUserServiceTest{
    @BeforeClass
    public static void addTitleToLog(){
        AbstractUserServiceTest.addTitleToLog(JpaUserServiceTest.class.getSimpleName());
    }
}