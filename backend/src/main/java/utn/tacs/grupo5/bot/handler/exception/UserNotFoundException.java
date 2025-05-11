package utn.tacs.grupo5.bot.handler.exception;

public class UserNotFoundException extends BotException {
    public UserNotFoundException(String message) {
        super(message);
    }
}
