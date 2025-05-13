package utn.tacs.grupo5.bot.handler.command.session;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;

/**
 * Command for handling registration state
 */
@Component
public class RegisterCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            handler.getBotService().registerUser(message.getText());
            handler.reply(chatId, "Registrado con éxito", KeyboardFactory.getCardsOption(), UserState.CHOOSING_OPTIONS);
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.REGISTERING);
        } catch (Exception e) {
            handler.reply(chatId, "Ha ocurrido un error inesperado. Intente nuevamente más tarde.", null, UserState.AWAITING_SESSION);
        }
    }
}
