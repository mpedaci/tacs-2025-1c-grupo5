package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;

/**
 * Command for handling choosing game state
 */
@Component
public class ChoosingGameCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        handler.getChatData().get(chatId).setGame(message.getText());
        handler.reply(chatId, "Elija la carta (el nombre debe ser exacto)", null, UserState.CHOOSING_CARD);
    }
}

