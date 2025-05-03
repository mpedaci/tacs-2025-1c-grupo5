package utn.tacs.grupo5.repository.impl;

import java.util.*;

import org.springframework.stereotype.Repository;

import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.repository.UserRepository;

@Repository
public class InMemoryUserRepository implements UserRepository {

    private final List<User> users = Collections.synchronizedList(new ArrayList<>());

    @Override
    public User save(User user) {
        if (user.getId() == null) {
            user.setId(UUID.randomUUID());
        } else {
            deleteById(user.getId()); // replace if exists
        }
        users.add(user);
        return user;
    }

    @Override
    public Optional<User> findById(UUID id) {
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
    public void deleteById(UUID id) {
        synchronized (users) {
            users.removeIf(user -> user.getId().equals(id));
        }
    }

    @Override
    public Optional<User> findByUsername(String username) {
        synchronized (users) {
            return users.stream()
                    .filter(user -> user.getUsername().equalsIgnoreCase(username))
                    .findFirst();
        }
    }

}
