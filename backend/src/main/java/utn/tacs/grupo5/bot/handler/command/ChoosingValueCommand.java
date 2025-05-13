package utn.tacs.grupo5.bot.handler.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.exception.BotException; /**
 * Command for handling choosing value state
 */
@Component
public class ChoosingValueCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            handler.getBotService().saveCardValue(
                    message.getText(),
                    handler.getChatData().get(chatId).getPostInputDto(),
                    handler.getChatData().get(chatId).getGame(),
                    handler.getChatData().get(chatId).getHelpStringValue()
            );
            handler.reply(chatId, "Ingrese una descripción de la publicación", null, UserState.CHOOSING_DESCRIPTION);
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.CHOOSING_VALUE);
        }
    }
}
