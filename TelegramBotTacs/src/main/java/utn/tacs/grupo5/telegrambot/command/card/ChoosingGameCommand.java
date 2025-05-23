package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;
import utn.tacs.grupo5.telegrambot.service.impl.BotService;


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
        try{
            String gameId = botService.findGame(message.getText());
            handler.getChatData().get(chatId).setGameId(gameId);
        }catch (RuntimeException e){
            throw new BotException("Juego no encontrado");
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Elija el juego", KeyboardFactory.getGameOption());
    }
}

