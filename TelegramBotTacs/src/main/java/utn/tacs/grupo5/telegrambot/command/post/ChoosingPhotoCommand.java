package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.exception.BotException;

import java.util.List;
import java.util.Objects; /**
 * Command for handling choosing photo publication state
 */
@Component
public class ChoosingPhotoCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        if (Objects.equals(message.getText(), "Si")) {
            handler.reply(chatId, "Envíe las fotos", null, UserState.CHOOSING_PHOTO);
        } else if (Objects.equals(message.getText(), "No")) {
            handler.reply(chatId, "No se enviarán fotos", KeyboardFactory.getCardValueOption(),
                    UserState.CHOOSING_VALUE_TYPE);
        }else throw new BotException("Opcion no valida");

    }

    @Override
    public void handlePhoto(long chatId, List<PhotoSize> photos, ResponseHandler handler) {
        List<String> savedPhotos = handler.getBotService().savePhotos(photos);
        handler.getChatData().get(chatId).setImages(savedPhotos);
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Desea ingresar imagenes de la carta?",
                KeyboardFactory.getYesOrNo());
    }
}
