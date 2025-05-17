package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.service.impl.BotService;

import java.util.Optional;
import java.util.UUID;

/**
 * Command for handling choosing game state
 */
@Component
public class ChoosingGameCommand implements StateCommand {
    private final BotService botService;

    public ChoosingGameCommand(BotService botService) {
        this.botService = botService;
    }

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        Optional<UUID> gameId = botService.findGame(message.getText());
        if (gameId.isPresent()) {
            handler.getChatData().get(chatId).setGame(message.getText());
        }else throw new BotException("Juego no encontrado");

    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija el juego", KeyboardFactory.getGameOption());
    }
}

