package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.util.List;

public interface MealsDAO {
    List<Meal> findAll();
    Meal save(Meal meal);
    Meal update(Meal meal, int id);
    void delete(int id);
    Meal getById(int id);
}
