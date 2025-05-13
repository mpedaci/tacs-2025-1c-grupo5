package utn.tacs.grupo5.bot.handler.command.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;

/**
 * Command for handling awaiting session state
 */
@Component
public class AwaitingSessionCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        if ("Log In".equalsIgnoreCase(message.getText())) {
            handler.reply(chatId, "ingrese su usuario y contraseña \n->usuario, contraseña", null, UserState.LOGIN_IN);
        } else if ("Registrarse".equalsIgnoreCase(message.getText())) {
            handler.reply(chatId, "Ingrese: \n-> nombre, username, password", null, UserState.REGISTERING);
        }
    }
}
