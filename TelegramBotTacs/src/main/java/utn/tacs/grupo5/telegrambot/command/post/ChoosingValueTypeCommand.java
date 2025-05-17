package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;

/**
 * Command for handling choosing value type state
 */
@Component
public class ChoosingValueTypeCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        switch (message.getText()) { //TODO refactor
            case "Dinero" -> {
                handler.getChatData().get(chatId).setHelpStringValue(message.getText());
            }
            case "Cartas" -> {
                handler.getChatData().get(chatId).setHelpStringValue(message.getText());
            }
            case "Ambos" -> {
                handler.getChatData().get(chatId).setHelpStringValue(message.getText());
            }
            default -> {throw new BotException("Opcion no valida");
            }
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija el tipo de intercambio", KeyboardFactory.getCardValueOption());
    }
}

