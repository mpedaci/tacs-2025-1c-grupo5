package utn.tacs.grupo5.repository.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.atomic.AtomicLong;

import org.springframework.stereotype.Repository;

import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.repository.UserRepository;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());
    private final AtomicLong idGenerator = new AtomicLong();

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(idGenerator.incrementAndGet());
        } else {
            deleteById(user.getId()); // replace if exists
        }
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(Long id) {
        synchronized (users) {
            return users.stream()
                    .filter(user -> user.getId().equals(id))
                    .findFirst();
        }
    }

    @Override
    public List<User> findAll() {
        synchronized (users) {
            return new ArrayList<>(users);
        }
    }

    @Override
    public void deleteById(Long id) {
        synchronized (users) {
            users.removeIf(user -> user.getId().equals(id));
        }
    }

    @Override
    public Optional<User> findByEmail(String email) {
        synchronized (users) {
            return users.stream()
                    .filter(user -> user.getEmail().equalsIgnoreCase(email))
                    .findFirst();
        }
    }

}
