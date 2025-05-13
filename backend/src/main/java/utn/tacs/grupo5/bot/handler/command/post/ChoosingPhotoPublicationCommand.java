package utn.tacs.grupo5.bot.handler.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;

import java.util.List;
import java.util.Objects; /**
 * Command for handling choosing photo publication state
 */
@Component
public class ChoosingPhotoPublicationCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        if (Objects.equals(message.getText(), "Si")) {
            handler.reply(chatId, "Envíe las fotos", null, UserState.CHOOSING_PHOTO_PUBLICATION);
        } else {
            handler.reply(chatId, "No se enviarán fotos \nElija el tipo de intercambio", KeyboardFactory.getCardValueOption(),
                    UserState.CHOOSING_VALUE_TYPE);
        }
    }

    @Override
    public void handlePhoto(long chatId, List<PhotoSize> photos, ResponseHandler handler) {
        List<String> savedPhotos = handler.getBotService().savePhotos(photos);
        handler.getChatData().get(chatId).getPostInputDto().setImages(savedPhotos);
        handler.reply(chatId, "Elija el tipo de intercambio", KeyboardFactory.getCardValueOption(),
                UserState.CHOOSING_VALUE_TYPE);
    }
}
