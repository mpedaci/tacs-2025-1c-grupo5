package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;



@Component
public class SavePostCommand implements StateCommand {

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            ChatData chatData = handler.getChatData().get(chatId);
            String postid = handler.getBotService().createPost(chatData);
            handler.getChatData().get(chatId).setPublicationId(postid);
            handler.reply(chatId, "Publicación creada con éxito", null);
            handler.reply(chatId, "ID de publicación: " + postid, null, UserState.CHOOSING_OPTIONS);

            //TODO devolver imagen del post junto con el link
        } catch (BotException e) {
            throw new BotException(e.getMessage());
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        execute(chatId,null,handler);
    }
}
