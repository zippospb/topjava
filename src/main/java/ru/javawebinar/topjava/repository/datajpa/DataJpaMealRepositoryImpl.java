package ru.javawebinar.topjava.repository.datajpa;

import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Example;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.MealRepository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public class DataJpaMealRepositoryImpl implements MealRepository {
    private static final Sort SORT_BY_DATETIME = Sort.by(Sort.Direction.DESC, "datetime");

    @PersistenceContext
    private EntityManager em;

    private CrudMealRepository crudRepository;

    @Autowired
    public DataJpaMealRepositoryImpl(CrudMealRepository crudRepository) {
        this.crudRepository = crudRepository;
    }

    @Override
    @Transactional
    public Meal save(Meal meal, int userId) {
        if (!meal.isNew() && get(meal.getId(), userId) == null) {
            return null;
        }
        meal.setUser(em.getReference(User.class, userId));
        return crudRepository.save(meal);
    }

    @Override
    public boolean delete(int id, int userId) {
        return crudRepository.delete(id, userId) > 0;
    }

    @Override
    public Meal get(int id, int userId) {
        return crudRepository.findById(id)
                .filter(m -> m.getUser().getId() != null && m.getUser().getId() == userId)
                .orElse(null);
    }

    @Override
    public List<Meal> getAll(int userId) {
        return crudRepository.findAll(userId);
    }

    @Override
    public List<Meal> getBetween(LocalDateTime startDate, LocalDateTime endDate, int userId) {
        return crudRepository.findAllBetween(userId, startDate, endDate);
    }

    @Override
    @Transactional(readOnly = true)
    public Meal getWithUser(int id, int userId) {
        Meal meal = get(id, userId);
        meal.setUser((User)Hibernate.unproxy(meal.getUser()));
        return meal;
    }
}
