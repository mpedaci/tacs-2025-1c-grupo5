package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.bots.AbsSender;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;
import java.util.Objects;

/**
 * Command for handling photo option selection (Si/No)
 * Only handles the decision, not the actual photo processing
 */
@Component
public class ChoosingPhotoCommand implements StateCommand {

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        String messageText = message.getText();

        if (Objects.equals(messageText, "Si")) {
            // Set flag to collect photos
            handler.getChatData().get(chatId).setShouldCollectPhotos(true);
            handler.reply(chatId, "✅ Perfecto! A continuación podrá enviar las fotos.", null);
            // Flow manager will handle transition to CHOOSING_PHOTO state

        } else if (Objects.equals(messageText, "No")) {
            // Set flag to not collect photos
            handler.getChatData().get(chatId).setShouldCollectPhotos(false);
            handler.reply(chatId, "✅ Entendido, continuaremos sin fotos.", null);
            // Flow manager will handle transition to next state (skipping photo collection)

        } else {
            throw new BotException("Por favor seleccione 'Si' o 'No' usando los botones.");
        }
    }

    @Override
    public void handlePhoto(long chatId, List<PhotoSize> photos, ResponseHandler handler, AbsSender absSender) {
        // This command doesn't handle photos - redirect user
        handler.reply(chatId, "⚠️ Primero debe seleccionar si desea agregar fotos usando los botones 'Si' o 'No'.",
                KeyboardFactory.getYesOrNo());
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.getChatData().get(chatId).getReplyKeyboard(); // Store current keyboard
        handler.reply(chatId, "¿Desea agregar imágenes de la carta?", KeyboardFactory.getYesOrNo());
    }
}