package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.NotFoundException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.UUID;

/**
 * Command for handling login state
 */
@Component
public class LoginCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        String[] credentials = message.getText().split(",");
        String username = credentials[0].trim();
        String password = credentials[1].trim();
        try {
            String userId = handler.getBotService().logInUser(username, password);
            handler.getChatData().get(chatId).setUserId(userId);
            String stringResponse = "Bienvenido " + username + "!";
            handler.reply(chatId, stringResponse, KeyboardFactory.getCardsOption());
        }catch (WebClientResponseException e){
            throw new NotFoundException("Usuario " + username);
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "ingrese su usuario y contraseña \n->usuario, contraseña", null);
    }
}
