package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import utn.tacs.grupo5.bot.handler.exception.*;
import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.security.JwtUtil;
import utn.tacs.grupo5.service.IBotService;

import java.util.Optional;

@Service
public class BotService implements IBotService {

    private AuthService authService;
    UserService userService;
    JwtUtil jwtUtil;

    public BotService(AuthService authService, UserService userService, JwtUtil jwtUtil) {
        this.authService = authService;
        this.userService = userService;
        this.jwtUtil = jwtUtil;
    }

    public boolean findExistingUsername(String text) {
        return userService.findByUsername(text).isPresent();
    }

    public Optional<User> findUser(String text) throws BotException {
        String[] string = text.split(", ", 2);
        if (string.length < 2) {
            throw new BadUserInputException("Formato inválido. \nUse: usuario, contraseña");
        }

        String username = string[0].trim();
        String password = string[1].trim();

        AuthInputDto authInputDto = new AuthInputDto(username, password);
        AuthOutputDto token = authService.login(authInputDto);
        String user = jwtUtil.getUsernameFromToken(token.getToken());

        return userService.findByUsername(user)
                .or(() -> { throw new UserNotFoundException("Usuario no encontrado tras autenticación."); });
    }

    public void registerUser(String text) throws BotException {
        String[] string = text.split(", ", 3);
        if (string.length < 3) {
            throw new BadUserInputException("Formato inválido. \nUse: nombre, username, password");
        }

        String name = string[0].trim();
        String username = string[1].trim();
        String password = string[2].trim();

        if (findExistingUsername(username)) {
            throw new InvalidUsernameException("El nombre de usuario ya existe.");
        }

        validatePassword(password);

        UserInputDto userInputDto = new UserInputDto(name, username, password, false);
        userService.save(userInputDto);
    }

    private void validatePassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("La contraseña debe contener al menos una letra minúscula.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("La contraseña debe contener al menos un número.");
        }
    }
}
