package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;
import utn.tacs.grupo5.telegrambot.validator.ValidatedStateCommand;
import utn.tacs.grupo5.telegrambot.validator.ValueTypeValidator;

/**
 * Command for handling choosing value type state
 */
@Component
public class ChoosingValueTypeCommand extends ValidatedStateCommand {

    public ChoosingValueTypeCommand(ValueTypeValidator validator) {
        super(validator);
    }

    @Override
    protected void executeValidated(long chatId, Message message, ResponseHandler handler, ChatData chatData) {
        chatData.setHelpStringValue(message.getText());
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija el tipo de intercambio", KeyboardFactory.getCardValueOption());
    }
}

