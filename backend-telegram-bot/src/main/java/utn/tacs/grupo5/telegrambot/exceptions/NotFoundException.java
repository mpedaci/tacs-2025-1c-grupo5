package utn.tacs.grupo5.telegrambot.exception;

public class NotFoundException extends BotException {
    public NotFoundException(String message) {
        super(message + " no encontrado");
    }
}
