package utn.tacs.grupo5.telegrambot.command.offer;

import org.springframework.stereotype.Component;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;

@Component
public class SaveOfferCommand implements StateCommand {
    @Override
    public void execute(long chatId, org.telegram.telegrambots.meta.api.objects.Message message, utn.tacs.grupo5.telegrambot.handler.ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        handler.getBotService().createOffer(chatData);
        handler.reply(chatId, "✅ Oferta guardada exitosamente.", null);
    }

    @Override
    public void onEnter(long chatId, utn.tacs.grupo5.telegrambot.handler.ResponseHandler handler) {
    }
}