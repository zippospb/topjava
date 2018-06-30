package ru.javawebinar.topjava.DAO;

import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.time.Month;
import java.util.Arrays;
import java.util.List;

public class RamMealsDAO implements MealsDAO{
    private List<Meal> meals = Arrays.asList(
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 10, 0), "Завтрак", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 13, 0), "Обед", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 30, 20, 0), "Ужин", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 10, 0), "Завтрак", 1000),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 13, 0), "Обед", 500),
            new Meal(LocalDateTime.of(2015, Month.MAY, 31, 20, 0), "Ужин", 510));

    @Override
    public List<Meal> findAll() {
        return meals;
    }

    @Override
    public Meal save(Meal meal) {
        meal.setId(meals.size());
        meals.add(meal);
        return meal;
    }

    @Override
    public Meal update(Meal meal, int id) {
        if(id < 0 || id >= meals.size()){
            throw new IllegalArgumentException();
        }
        meal.setId(id);
        meals.set(id, meal);
        return null;
    }

    @Override
    public void delete(Meal meal) {
        meals.remove(meal);
    }
}
