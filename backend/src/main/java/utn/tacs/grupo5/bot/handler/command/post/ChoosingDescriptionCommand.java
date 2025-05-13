package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException; /**
 * Command for handling choosing description state
 */
@Component
public class ChoosingDescriptionCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        handler.getChatData().get(chatId).getPostInputDto().setDescription(message.getText());
        try {
            handler.getBotService().createPost(handler.getChatData().get(chatId).getPostInputDto());
            handler.reply(chatId, "Publicación creada con éxito", KeyboardFactory.getCardsOption(), UserState.CHOOSING_OPTIONS);
            //TODO deberia devolver el link a la publicacion
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.CHOOSING_OPTIONS);
        }
    }
}
