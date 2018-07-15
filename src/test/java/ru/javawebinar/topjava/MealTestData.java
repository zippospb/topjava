package ru.javawebinar.topjava;

import org.assertj.core.internal.FieldByFieldComparator;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static ru.javawebinar.topjava.model.AbstractBaseEntity.START_SEQ;

public class MealTestData {


    public static final List<Meal> MEALS = Arrays.asList(
            new Meal(START_SEQ + 7, LocalDateTime.of(2015, 5, 31, 20, 0), "Ужин", 510),
            new Meal(START_SEQ + 6, LocalDateTime.of(2015, 5, 31, 13, 0), "Обед", 500),
            new Meal(START_SEQ + 5, LocalDateTime.of(2015, 5, 31, 10, 0), "Завтрак", 1000),
            new Meal(START_SEQ + 4, LocalDateTime.of(2015, 5, 30, 20, 0), "Ужин", 500),
            new Meal(START_SEQ + 3, LocalDateTime.of(2015, 5, 30, 13, 0), "Обед", 1000),
            new Meal(START_SEQ + 2, LocalDateTime.of(2015, 5, 30, 10, 0), "Завтрак", 500));



    public static void assertMatch(Iterable<Meal> actual, Iterable<Meal> expected) {
        assertThat(actual).usingElementComparator(new FieldByFieldComparator()).isEqualTo(expected);
    }

    public static void assertMatch(Meal actual, Meal expected){
        assertThat(actual).isNotNull().usingComparator(new FieldByFieldComparator()).isEqualTo(expected);
    }
}
