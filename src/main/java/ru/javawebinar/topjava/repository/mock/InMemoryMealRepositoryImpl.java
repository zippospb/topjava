package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.repository.MealRepository;
import ru.javawebinar.topjava.util.DateTimeUtil;
import ru.javawebinar.topjava.util.MealsUtil;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Repository
public class InMemoryMealRepositoryImpl implements MealRepository {
    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);
    private Map<Integer, Map<Integer, Meal>> repository = new ConcurrentHashMap<>();

    private AtomicInteger counter = new AtomicInteger(0);

    {
        MealsUtil.MEALS.forEach(meal -> save(1, meal));
    }

    @Override
    public Meal save(int userId, Meal meal) {
        log.info("save meal{}", meal);
        Map<Integer, Meal> meals = getAllByUser(userId);
        if(!meal.isNew() && !meals.containsKey(meal.getId())){
            return null;
        }
        if (meal.isNew()) {
            meal.setId(counter.incrementAndGet());
            meals.put(meal.getId(), meal);
            return meal;
        }
        return meals.computeIfPresent(meal.getId(), (id, oldMeal) -> meal);
    }

    @Override
    public boolean delete(int userId, int id) {
        log.info("delete meal {}", id);
        Map<Integer, Meal> meals = getAllByUser(userId);
        return meals.remove(id) != null;
    }

    @Override
    public Meal get(int userId, int id) {
        log.info("get meal {}", id);
        return getAllByUser(userId).get(id);
    }

    @Override
    public List<Meal> getAllByDate(int userId, LocalDate fromDate, LocalDate toDate) {
        log.info("getAllMealsByDate");
        return getSortedCollection(getAllByUser(userId).values().stream()
                .filter(meal -> DateTimeUtil.isBetween(meal.getDate(), fromDate, toDate)));
    }

    private Map<Integer, Meal> getAllByUser(int userId){
        return repository.computeIfAbsent(userId, (k) -> new ConcurrentHashMap<>());
    }

    @Override
    public List<Meal> getAll(int userId) {
        return getSortedCollection(getAllByUser(userId).values().stream());
    }

    private List<Meal> getSortedCollection(Stream<Meal> stream){
        return stream.sorted(Comparator.comparing(Meal::getDate).reversed())
                .collect(Collectors.toList());
    }
}

