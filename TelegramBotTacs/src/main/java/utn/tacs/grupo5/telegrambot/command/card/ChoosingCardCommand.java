package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.CardSelectionContext;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;

/**
 * Command for handling choosing card state
 */
@Component
public class ChoosingCardCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            ChatData chatData = handler.getChatData().get(chatId);
            List<CardOutputDTO> cards = handler.getBotService().findCard(chatData.getGameId(), message.getText());
            chatData.setCurrentCards(cards);
            chatData.setCurrentIndex(0); // Reset index

            if(cards.size() > 1) {
                chatData.setCardSelectionContext(CardSelectionContext.CHOOSING_OFFERED_CARD);
                chatData.setNeedsMoreCardSelection(true);
            } else {
                chatData.setCardId(cards.get(0).getId());
            }
        } catch (BotException e) {
            throw new BotException(e.getMessage());
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija la carta que está ofreciendo (el nombre debe ser exacto)", null);
    }
}