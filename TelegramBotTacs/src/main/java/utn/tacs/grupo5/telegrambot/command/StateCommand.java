package utn.tacs.grupo5.telegrambot.command;

import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.exception.BotException;

import java.util.List;

/**
 * Interface for all state commands
 * This is part of the Command Pattern implementation
 */
public interface StateCommand {
    /**
     * Execute the command for the given message
     *
     * @param chatId The chat ID
     * @param message The message received
     * @param handler The response handler context
     */
    void execute(long chatId, Message message, ResponseHandler handler) throws BotException;

    /**
     * Handle photo uploads for the current state
     * By default, this does nothing - override in states that need photo handling
     *
     * @param chatId The chat ID
     * @param photos The photos received
     * @param handler The response handler context
     */
    default void handlePhoto(long chatId, List<PhotoSize> photos, ResponseHandler handler) {
        // Default implementation does nothing
    }

    void onEnter(long chatId, ResponseHandler handler);
}