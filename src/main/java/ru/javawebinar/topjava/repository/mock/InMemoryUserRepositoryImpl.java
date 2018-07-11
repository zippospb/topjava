package ru.javawebinar.topjava.repository.mock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;
import ru.javawebinar.topjava.model.AbstractBaseEntity;
import ru.javawebinar.topjava.model.AbstractNamedEntity;
import ru.javawebinar.topjava.model.User;
import ru.javawebinar.topjava.repository.UserRepository;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicInteger;

@Repository
public class InMemoryUserRepositoryImpl implements UserRepository {
    private Map<Integer, User> repository = new ConcurrentHashMap<>();
    private AtomicInteger counter = new AtomicInteger(0);

    private static final Logger log = LoggerFactory.getLogger(InMemoryUserRepositoryImpl.class);

    @Override
    public boolean delete(int id) {
        log.info("delete user {}", id);
        return repository.remove(id) != null;
    }

    @Override
    public User save(User user) {
        log.info("save user {}", user);

        if(user.isNew()){
            user.setId(counter.incrementAndGet());
        }
        return repository.computeIfPresent(user.getId(), (id, oldUser) -> user);
    }

    @Override
    public User get(int id) {
        log.info("get user {}", id);
        return repository.get(id);
    }

    @Override
    public List<User> getAll() {
        log.info("getAllUsers");
        List<User> users = new ArrayList<>(repository.values());
        users.sort(Comparator.comparing(AbstractNamedEntity::getName)
                .thenComparing(AbstractBaseEntity::getId));
        return users;
    }

    @Override
    public User getByEmail(String email) {
        log.info("getUserByEmail {}", email);
        return repository.values().stream()
                .filter(user -> user.getEmail().equals(email))
                .findFirst()
                .orElse(null);
    }
}
