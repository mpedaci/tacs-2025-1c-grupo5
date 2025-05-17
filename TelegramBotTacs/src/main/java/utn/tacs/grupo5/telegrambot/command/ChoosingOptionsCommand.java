package utn.tacs.grupo5.telegrambot.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.exception.BadUserInputException;

/**
 * Command for handling choosing options state
 */
@Component
public class ChoosingOptionsCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        if ("Publicar Carta".equalsIgnoreCase(message.getText())) {
            handler.getChatData().get(chatId).setUserId(handler.getChatData().get(chatId).getUser());
            handler.getChatData().get(chatId).setFlow("post");
        }
        else if ("Hacer Una Oferta".equals(message.getText())) {
            handler.getChatData().get(chatId).setFlow("offer");
        }
        else throw new BadUserInputException("Elija una opción válida");
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija una opción", KeyboardFactory.getCardsOption(), UserState.CHOOSING_OPTIONS);
    }
}