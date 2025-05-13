package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;

/**
 * Command for handling choosing options state
 */
@Component
public class ChoosingOptionsCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        if ("Publicar Carta".equalsIgnoreCase(message.getText())) {
            handler.getChatData().get(chatId).getPostInputDto().setUserId(handler.getChatData().get(chatId).getUser());
            handler.reply(chatId, "Elija el juego", KeyboardFactory.getGameOption(), UserState.CHOOSING_GAME);
        }
    }
}