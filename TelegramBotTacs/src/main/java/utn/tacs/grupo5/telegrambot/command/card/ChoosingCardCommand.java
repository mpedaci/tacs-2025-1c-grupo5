package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.entity.card.Card;

/**
 * Command for handling choosing card state
 */
@Component
public class ChoosingCardCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            Card card = handler.getBotService().findCard(handler.getChatData().get(chatId).getGame(), message.getText());
            handler.getChatData().get(chatId).setCardId(card.getId());

        } catch (BotException e) {
            throw new BotException(e.getMessage());
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija la carta (el nombre debe ser exacto)", null);
    }
}
