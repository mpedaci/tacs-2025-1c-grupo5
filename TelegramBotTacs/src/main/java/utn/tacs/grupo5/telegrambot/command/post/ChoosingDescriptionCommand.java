package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;

/**
 * Command for handling choosing description state
 */
@Component
public class ChoosingDescriptionCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        handler.getChatData().get(chatId).setDescription(message.getText());
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Ingrese una descripción de la publicación", null);
    }
}
