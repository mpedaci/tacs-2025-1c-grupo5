package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.User;
import utn.tacs.grupo5.service.IBotService;

import java.util.Optional;

@Service
public class BotService implements IBotService {

    private UserService userService;

    public BotService() {}

    public boolean findExistingUsername(String text) {
        return false;
    }

    public Optional<User> findUser(String text) {
        return Optional.empty();
    }
}
