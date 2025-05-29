package utn.tacs.grupo5.telegrambot.validator;

import org.springframework.stereotype.Component;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;

import java.util.Set;

@Component
public class ValueTypeValidator implements InputValidator<String> {
    private static final Set<String> VALID_OPTIONS = Set.of("Dinero", "Cartas", "Ambos");

    @Override
    public ValidationResult validate(String input, ChatData chatData) {
        if (input == null || !VALID_OPTIONS.contains(input)) {
            return ValidationResult.failure("Opción no válida. Seleccione: Dinero, Cartas o Ambos");
        }
        return ValidationResult.success();
    }
}