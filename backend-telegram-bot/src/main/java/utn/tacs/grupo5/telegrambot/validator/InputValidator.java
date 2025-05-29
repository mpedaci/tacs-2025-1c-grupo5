package utn.tacs.grupo5.telegrambot.validator;

import utn.tacs.grupo5.telegrambot.telegram.ChatData;

public interface InputValidator<T> {
    ValidationResult validate(T input, ChatData chatData);
}
