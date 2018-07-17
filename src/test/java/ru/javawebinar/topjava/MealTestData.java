package ru.javawebinar.topjava;

import org.assertj.core.internal.FieldByFieldComparator;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {

    public final static Meal USER_MEAL = new Meal(START_SEQ + 7, LocalDateTime.of(2015, 5, 31, 20, 0), "Ужин", 510);
    public final static Meal USER_MEAL2 = new Meal(START_SEQ + 6, LocalDateTime.of(2015, 5, 31, 13, 0), "Обед", 500);
    public final static Meal USER_MEAL3 = new Meal(START_SEQ + 5, LocalDateTime.of(2015, 5, 31, 10, 0), "Завтрак", 1000);
    public final static Meal USER_MEAL4 = new Meal(START_SEQ + 4, LocalDateTime.of(2015, 5, 30, 20, 0), "Ужин", 500);
    public final static Meal USER_MEAL5 = new Meal(START_SEQ + 3, LocalDateTime.of(2015, 5, 30, 13, 0), "Обед", 1000);
    public final static Meal USER_MEAL6 = new Meal(START_SEQ + 2, LocalDateTime.of(2015, 5, 30, 10, 0), "Завтрак", 500);
    public final static Meal NEW_MEAL = new Meal(LocalDateTime.of(2016, 5, 30, 10, 0), "Дожорчик", 5000);
    public final static Meal NOT_UNIQUE_DATE_MEAL = new Meal(LocalDateTime.of(2015, 5, 31, 13, 0), "", 0);
    public final static int MEAL_ID = START_SEQ + 7;

    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingFieldByFieldElementComparator().isEqualTo(expected);
    }

    public static void assertMatch(Iterable<Meal> actual, Meal... expected) {
        assertMatch(actual, Arrays.asList(expected));
    }

    public static void assertMatch(Meal actual, Meal expected){
        assertThat(actual).isNotNull().usingComparator(new FieldByFieldComparator()).isEqualTo(expected);
    }
}
