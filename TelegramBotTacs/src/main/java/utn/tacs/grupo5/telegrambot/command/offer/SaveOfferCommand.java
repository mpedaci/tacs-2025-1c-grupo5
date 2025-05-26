package utn.tacs.grupo5.telegrambot.command.offer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import static utn.tacs.grupo5.telegrambot.UserState.CHOOSING_OPTIONS;

@Component
public class SaveOfferCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        String offerId = handler.getBotService().createOffer(chatData);
        handler.reply(chatId, "✅ Oferta guardada exitosamente.", null);
        handler.reply(chatId, offerId, null);
        handler.reply(chatId, "Volviendo al menú principal...", KeyboardFactory.getCardsOption(), CHOOSING_OPTIONS);
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        execute(chatId, null, handler);
    }
}