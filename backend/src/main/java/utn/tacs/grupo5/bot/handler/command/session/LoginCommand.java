package utn.tacs.grupo5.bot.handler.command.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;

/**
 * Command for handling login state
 */
@Component
public class LoginCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            var user = handler.getBotService().findUser(message.getText());
            if (user.isPresent()) {
                handler.getChatData().get(chatId).setUser(user.get().getId());
                String stringResponse = "Bienvenido " + user.get().getName();
                handler.reply(chatId, stringResponse, KeyboardFactory.getCardsOption(), UserState.CHOOSING_OPTIONS);
            }
        } catch (utn.tacs.grupo5.bot.handler.exception.BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.LOGIN_IN);
        }
    }
}
