package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.NotFoundException;

/**
 * Command for handling login state
 */
@Component
public class LoginCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        String username = message.getText();
        var user = handler.getBotService().findUser(username);
            if (user.isPresent()) {
                handler.getChatData().get(chatId).setUser(user.get().getId());
                String stringResponse = "Bienvenido " + user.get().getName();
                handler.reply(chatId, stringResponse, KeyboardFactory.getCardsOption());
        } else {
            throw new NotFoundException("Usuario " + username);
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "ingrese su usuario y contraseña \n->usuario, contraseña", null);
    }
}
