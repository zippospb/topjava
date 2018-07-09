package ru.javawebinar.topjava.repository;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDate;
import java.util.List;

public interface MealRepository {
    Meal save(int userId, Meal meal);

    Meal delete(int userId, int id);

    Meal get(int userId, int id);

    List<Meal> getAllByDate(int userId, LocalDate fromDate, LocalDate toDate);

    List<Meal> getAll(int userId);
}
