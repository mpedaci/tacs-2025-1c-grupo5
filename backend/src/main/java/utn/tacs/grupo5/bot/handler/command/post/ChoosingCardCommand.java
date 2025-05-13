package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.entity.card.Card; /**
 * Command for handling choosing card state
 */
@Component
public class ChoosingCardCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            Card card = handler.getBotService().findCard(handler.getChatData().get(chatId).getGame(), message.getText());
            handler.getChatData().get(chatId).getPostInputDto().setCardId(card.getId());
            handler.reply(chatId, "Elija el estado de conservacion de la carta",
                    KeyboardFactory.getCardConditionOption(), UserState.CHOOSING_CONDITION);
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.CHOOSING_CARD);
        }
    }
}
