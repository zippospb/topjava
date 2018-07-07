package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;
import ru.javawebinar.topjava.web.SecurityUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    private Map<Integer, Meal> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(this::save);
    }

    @Override
    public Meal save(Meal meal) {
        log.info("save meal{}", meal);
        meal.setUserId(SecurityUtil.authUserId());
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            repository.put(meal.getId(), meal);
            return meal;
        }
        // treat case: update, but absent in storage
        return repository.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int id) {
        log.info("delete meal {}", id);
        Meal meal = repository.get(id);
        if(meal != null && meal.getUserId() == SecurityUtil.authUserId()){
            repository.remove(id);
            return true;
        }
        return false;
    }

    @Override
    public Meal get(int id) {
        log.info("get meal {}", id);

        Meal meal = repository.get(id);
        return meal != null && meal.getUserId() == SecurityUtil.authUserId() ? meal : null;
    }

    @Override
    public List<Meal> getAll(LocalDate fromDate, LocalDate toDate) {
        log.info("getAllMeals");
        return repository.values().stream()
                .filter(meal -> meal.getUserId() == SecurityUtil.authUserId())
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), fromDate, toDate))
                .sorted(Comparator.comparing(Meal::getDate))
                .collect(Collectors.toList());
    }

}

