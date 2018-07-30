package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Override
    Meal save(Meal meal);

    @Transactional
    @Modifying
    @Query("DELETE from Meal m where m.id=?1 AND m.user.id=?2")
    int delete(int id, int userId);

    List<Meal> findByUserIdOrderByDateTimeDesc(int userId);

    List<Meal> findByUserIdAndDateTimeBetweenOrderByDateTimeDesc(int userId, LocalDateTime startDate, LocalDateTime endDate);

    Meal getByIdAndUserId(int id, int userId);

    @Query("SELECT m FROM Meal m JOIN FETCH m.user WHERE m.id=?1 AND m.user.id=?2")
    Meal getWithUser(int id, int userId);
}
