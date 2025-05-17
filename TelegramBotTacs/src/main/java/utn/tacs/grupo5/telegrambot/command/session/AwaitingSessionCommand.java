package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;

import static utn.tacs.grupo5.bot.Constants.START_TEXT;

/**
 * Command for handling awaiting session state
 */
@Component
public class AwaitingSessionCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        if ("Log In".equalsIgnoreCase(message.getText())) {
            handler.getChatData().get(chatId).setFlow("login");
        } else if ("Registrarse".equalsIgnoreCase(message.getText())) {
            handler.getChatData().get(chatId).setFlow("register");
        }else throw new BotException("Opcion no valida");
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, START_TEXT +"\n¿Ya tienes una cuenta?", KeyboardFactory.getStartOption(), UserState.AWAITING_SESSION);
    }
}
