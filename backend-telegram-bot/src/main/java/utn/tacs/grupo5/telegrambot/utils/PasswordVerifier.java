package utn.tacs.grupo5.telegrambot.util;


import utn.tacs.grupo5.telegrambot.exception.InvalidPasswordException;

public class PasswordVerifier {
    public static void validatePassword(String password) {
        if (password.length() < 8) {
            throw new InvalidPasswordException("debe tener al menos 8 caracteres.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("debe contener al menos una letra mayúscula.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("debe contener al menos una letra minúscula.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("debe contener al menos un número.");
        }
    }
}