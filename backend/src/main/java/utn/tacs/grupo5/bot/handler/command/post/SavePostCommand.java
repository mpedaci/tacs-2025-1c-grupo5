package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;

import java.util.UUID;


@Component
public class SavePostCommand implements StateCommand {

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            UUID postid = handler.getBotService().createPost(handler.getChatData().get(chatId).getPostInputDto());
            handler.getChatData().get(chatId).setPublicationId(postid);
            //TODO devolver imagen del post junto con el link
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.CHOOSING_OPTIONS);
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Publicación creada con éxito", KeyboardFactory.getCardsOption());
        execute(chatId,null,handler);
        handler.reply(chatId, handler.getChatData().get(chatId).getPublicationId().toString(), null, UserState.CHOOSING_OPTIONS);
    }
}
