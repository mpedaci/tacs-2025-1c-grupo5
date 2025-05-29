package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.service.IGameService;
import utn.tacs.grupo5.telegrambot.telegram.CardSelectionContext;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;

/**
 * Command for handling choosing card state
 */
@Component
public class ChoosingCardCommand implements StateCommand {
    private final IGameService gameService;

    public ChoosingCardCommand(IGameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            ChatData chatData = handler.getChatData().get(chatId);
            List<CardOutputDto> cards = gameService.findCards(chatData.getToken(), chatData.getGameId().toString(), message.getText());
            chatData.setCurrentCards(cards);
            chatData.setCurrentIndex(0); // Reset index

            if (cards.size() > 1) {
                chatData.setCardSelectionContext(CardSelectionContext.CHOOSING_OFFERED_CARD);
                chatData.setNeedsMoreCardSelection(true);
            } else {
                chatData.setCardId(cards.get(0).getId());
                chatData.setCardName(cards.get(0).getName());
            }
        } catch (BotException e) {
            throw new BotException(e.getMessage());
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Escriba el nombre de la carta", null);
    }
}