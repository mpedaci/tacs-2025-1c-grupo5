package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.dto.post.PostInputDto;
import utn.tacs.grupo5.telegrambot.dto.post.PostOutputDto;
import utn.tacs.grupo5.telegrambot.service.IPostService;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.telegram.UserState;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;


@Component
public class SavePostCommand implements StateCommand {
    private final IPostService postService;

    public SavePostCommand(IPostService postService) {
        this.postService = postService;
    }

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            ChatData chatData = handler.getChatData().get(chatId);
            PostInputDto postInputDto = new PostInputDto(
                    chatData.getUserId(),
                    chatData.getImages(),
                    chatData.getCardId(),
                    chatData.getConservationStatus(),
                    chatData.getEstimatedValue(),
                    chatData.getSelectedCardsIds(),
                    chatData.getDescription()
            );
            PostOutputDto post = postService.createPost(chatData.getToken(), postInputDto);
            handler.getChatData().get(chatId).setPublicationId(post.getId());
            handler.reply(chatId, "Publicación creada con éxito", null);
            handler.reply(chatId, "ID de publicación: " + post.getId().toString(), null, UserState.CHOOSING_OPTIONS);
            handler.reply(chatId, "Volviendo al menú principal...", KeyboardFactory.getCardsOption());
            //TODO devolver imagen del post junto con el link
        } catch (BotException e) {
            throw new BotException(e.getMessage());
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        execute(chatId, null, handler);
    }
}
