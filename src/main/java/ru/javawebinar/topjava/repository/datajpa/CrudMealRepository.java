package ru.javawebinar.topjava.repository.datajpa;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;
import ru.javawebinar.topjava.model.Meal;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Transactional(readOnly = true)
public interface CrudMealRepository extends JpaRepository<Meal, Integer> {

    @Transactional
    @Override
    <T extends Meal> T save(T meal);

    @Transactional
    @Modifying
    @Query("DELETE from Meal m where m.id=?1 AND m.user.id=?2")
    int delete(int id, int userId);

    @Override
    Optional<Meal> findById(Integer id);

    @Query("SELECT m FROM Meal m WHERE m.user.id=?1 ORDER BY m.dateTime DESC")
    List<Meal> findAll(int userId);

    @Query("SELECT m FROM Meal m WHERE m.user.id=?1 AND m.dateTime BETWEEN ?2 AND ?3 ORDER BY m.dateTime DESC")
    List<Meal> findAllBetween (int userId, LocalDateTime startDate, LocalDateTime endDate);
}
