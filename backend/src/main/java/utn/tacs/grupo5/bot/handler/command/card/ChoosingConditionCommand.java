package utn.tacs.grupo5.bot.handler.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.entity.post.ConservationStatus; /**
 * Command for handling choosing condition state
 */
@Component
public class ChoosingConditionCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            handler.getChatData().get(chatId).getPostInputDto()
                    .setConservationStatus(ConservationStatus.valueOf(message.getText().toUpperCase()));
        } catch (IllegalArgumentException e) {
            handler.reply(chatId, "Estado de conservación inválido. Intente nuevamente.",
                    null, UserState.CHOOSING_CONDITION);
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija el estado de conservacion de la carta",
                KeyboardFactory.getCardConditionOption());
    }
}
