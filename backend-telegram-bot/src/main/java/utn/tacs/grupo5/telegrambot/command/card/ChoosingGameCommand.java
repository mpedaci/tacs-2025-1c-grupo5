package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.game.GameOutputDto;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;
import utn.tacs.grupo5.telegrambot.service.IGameService;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;

import java.util.List;


/**
 * Command for handling choosing game state
 */
@Component
public class ChoosingGameCommand implements StateCommand {
    private final IGameService gameService;

    public ChoosingGameCommand(IGameService gameService) {
        this.gameService = gameService;
    }

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            ChatData chatData = handler.getChatData().get(chatId);
            List<GameOutputDto> games = gameService.getGames(chatData.getToken());
            GameOutputDto game = games.stream()
                    .filter(g -> g.getTitle().equalsIgnoreCase(message.getText()))
                    .findFirst()
                    .orElseThrow(() -> new BotException("Juego no encontrado"));
            handler.getChatData().get(chatId).setGameId(game.getId());
        } catch (RuntimeException e) {
            throw new BotException("Juego no encontrado");
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        List<GameOutputDto> games = gameService.getGames(chatData.getToken());
        handler.reply(chatId, "Elija el juego", KeyboardFactory.getGameOption(games));
    }
}

