package utn.tacs.grupo5.telegrambot.validator;

import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

public abstract class ValidatedStateCommand implements StateCommand {
    protected final InputValidator<String> validator;

    protected ValidatedStateCommand(InputValidator<String> validator) {
        this.validator = validator;
    }

    @Override
    public final void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            ChatData chatData = handler.getChatData().get(chatId);

            if (validator != null) {
                ValidationResult result = validator.validate(message.getText(), chatData);
                if (!result.isValid()) {
                    throw new BotException(result.getErrorMessage());
                }
            }

            executeValidated(chatId, message, handler, chatData);

        } catch (BotException e) {
            throw e; // Re-throw bot exceptions
        } catch (Exception e) {
            throw new BotException("Error procesando comando: " + e.getMessage());
        }
    }

    protected abstract void executeValidated(long chatId, Message message, ResponseHandler handler, ChatData chatData);
}