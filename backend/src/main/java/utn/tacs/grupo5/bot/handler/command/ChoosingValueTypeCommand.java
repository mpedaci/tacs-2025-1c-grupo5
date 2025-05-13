package utn.tacs.grupo5.bot.handler.command;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;

/**
 * Command for handling choosing value type state
 */
@Component
public class ChoosingValueTypeCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        String caseMoney = "Ingrese el monto \n-> monto";
        String caseCards = "Ingrese el nombre de la carta \n-> carta1, carta2, cartaN";

        switch (message.getText()) {
            case "Dinero" -> {
                handler.reply(chatId, caseMoney, null, UserState.CHOOSING_VALUE);
                handler.getChatData().get(chatId).setHelpStringValue(message.getText());
            }
            case "Cartas" -> {
                handler.reply(chatId, caseCards, null, UserState.CHOOSING_VALUE);
                handler.getChatData().get(chatId).setHelpStringValue(message.getText());
            }
            case "Ambos" -> {
                handler.reply(chatId, caseMoney + "\n" + caseCards, null, UserState.CHOOSING_VALUE);
                handler.getChatData().get(chatId).setHelpStringValue(message.getText());
            }
        }
    }
}

