package utn.tacs.grupo5.bot.handler.command.post;

import org.jetbrains.annotations.NotNull;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.bot.Chatdata;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.post.ConservationStatus;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;


@Component
public class SavePostCommand implements StateCommand {

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            Chatdata chatData = handler.getChatData().get(chatId);
            PostInputDto postInputDto = getPostInputDto(chatData);
            UUID postid = handler.getBotService().createPost(postInputDto);
            handler.getChatData().get(chatId).setPublicationId(postid);
            //TODO devolver imagen del post junto con el link
        } catch (BotException e) {
            handler.reply(chatId, e.getMessage(), null, UserState.CHOOSING_OPTIONS);
        }
    }

    @NotNull
    private static PostInputDto getPostInputDto(Chatdata chatData) {
        UUID userId = chatData.getUserId();
        List<String> images = chatData.getImages();
        UUID cardId = chatData.getCardId();
        ConservationStatus conservationStatus = chatData.getConservationStatus();
        BigDecimal estimatedValue = chatData.getEstimatedValue();
        List<UUID> wantedCardsIds = chatData.getWantedCardsIds();
        String description = chatData.getDescription();
        PostInputDto postInputDto = new PostInputDto(userId, images, cardId, conservationStatus, estimatedValue, wantedCardsIds, description);
        return postInputDto;
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Publicación creada con éxito", KeyboardFactory.getCardsOption());
        execute(chatId,null,handler);
        handler.reply(chatId, handler.getChatData().get(chatId).getPublicationId().toString(), null, UserState.CHOOSING_OPTIONS);
    }
}
