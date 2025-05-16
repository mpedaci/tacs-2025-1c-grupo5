package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
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
                    handler.getChatData().get(chatId),
                    handler.getChatData().get(chatId).getGame(),
                    handler.getChatData().get(chatId).getHelpStringValue()
            );
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.CHOOSING_VALUE);
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {  //TODO refactor
        String caseMoney = "Ingrese el monto \n-> monto";
        String caseCards = "Ingrese el nombre de la carta \n-> carta1, carta2, cartaN";

        switch (handler.getChatData().get(chatId).getHelpStringValue()) {
            case "Dinero" -> {
                handler.reply(chatId, caseMoney, null);
            }
            case "Cartas" -> {
                handler.reply(chatId, caseCards, null);
            }
            case "Ambos" -> {
                handler.reply(chatId, caseMoney + "\n" + caseCards, null);
            }
        }
    }
}
