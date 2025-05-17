package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;

/**
 * Command for handling registration state
 */
@Component
public class RegisterCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            handler.getBotService().registerUser(message.getText());
            handler.reply(chatId, "Registrado con éxito", KeyboardFactory.getCardsOption());
        } catch (BotException e) {
            throw new BotException(e.getMessage());
        }
    }
    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Ingrese: \n-> nombre, username, password", null);
    }
}
