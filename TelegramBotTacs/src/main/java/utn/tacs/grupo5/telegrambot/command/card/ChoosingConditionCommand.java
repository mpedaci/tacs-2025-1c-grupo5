package utn.tacs.grupo5.telegrambot.command.card;

import org.apache.commons.lang3.tuple.Pair;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.ConservationStatus;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

/**
 * Command for handling choosing condition state
 */
@Component
public class ChoosingConditionCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            handler.getChatData().get(chatId)
                    .setConservationStatus(ConservationStatus.fromString(message.getText()));
        } catch (IllegalArgumentException e) {
            throw new BotException("Estado de conservación inválido. Intente nuevamente.");
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija el estado de conservacion de la carta",
                KeyboardFactory.getCardConditionOption());
    }
}
