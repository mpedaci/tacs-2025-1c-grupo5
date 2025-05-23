package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

/**
 * Command for handling registration state
 */
@Component
public class RegisterCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            String[] credentials = message.getText().split(",");
            String name = credentials[0].trim();
            String username = credentials[1].trim();
            String password = credentials[2].trim();
            String userId = handler.getBotService().registerUser(name, username, password).getId();
            handler.getChatData().get(chatId).setUserId(userId);
            handler.reply(chatId, "Registrado con éxito", KeyboardFactory.getCardsOption());
        } catch (WebClientException e) {
            throw new BotException(e.getMessage());
        }
    }
    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Ingrese: \n-> nombre, username, password", null);
    }
}
