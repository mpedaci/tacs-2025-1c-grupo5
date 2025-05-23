package utn.tacs.grupo5.telegrambot.command.post;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.objects.File;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Comparator;
import java.util.List;

/**
 * Command for handling multiple photo collection state
 * Dedicated to processing and storing photos only
 */
@Component
public class ChoosingPhotoCollectionCommand implements StateCommand {

    private static final Logger logger = LoggerFactory.getLogger(ChoosingPhotoCollectionCommand.class);
    private static final int MAX_PHOTOS = 10; // Limit for safety

    @Value("${telegram.bot.token}")
    private String botToken;

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        String messageText = message.getText();

        if (messageText != null && messageText.equalsIgnoreCase("continuar")) {
            handleContinueCommand(chatId, handler);
        } else if (messageText != null && messageText.equalsIgnoreCase("omitir")) {
            handleSkipCommand(chatId, handler);
        } else {
            // Provide instructions
            int currentPhotoCount = handler.getChatData().get(chatId).getImages().size();
            handler.reply(chatId,
                    String.format("📸 Fotos guardadas: %d/%d\n\n" +
                                    "• Envíe las fotos una por una\n" +
                                    "• Escriba 'continuar' cuando termine\n" +
                                    "• Escriba 'omitir' para continuar sin más fotos",
                            currentPhotoCount, MAX_PHOTOS), null);
        }
    }

    @Override
    public void handlePhoto(long chatId, List<PhotoSize> photos, ResponseHandler handler, AbsSender absSender) {
        // Check photo limit
        int currentPhotoCount = handler.getChatData().get(chatId).getImages().size();
        if (currentPhotoCount >= MAX_PHOTOS) {
            handler.reply(chatId,
                    String.format("⚠️ Límite de fotos alcanzado (%d). Escriba 'continuar' para seguir.", MAX_PHOTOS),
                    null);
            return;
        }

        PhotoSize largestPhoto = photos.stream()
                .max(Comparator.comparing(PhotoSize::getFileSize))
                .orElse(null);

        if (largestPhoto == null) {
            handler.reply(chatId, "❌ Error al procesar la foto. Intente nuevamente.", null);
            return;
        }

        try {
            String base64Image = downloadAndEncodeImage(largestPhoto, absSender);

            // Add the image to chat data
            handler.getChatData().get(chatId).getImages().add(base64Image);

            // Give feedback to user
            int photoCount = handler.getChatData().get(chatId).getImages().size();
            handler.reply(chatId,
                    String.format("✅ Foto %d guardada correctamente.\n" +
                            "📸 Envíe otra foto o escriba 'continuar' para seguir.", photoCount),
                    null);

        } catch (Exception e) {
            logger.error("Error processing photo for chat {}: {}", chatId, e.getMessage(), e);
            handler.reply(chatId, "❌ Error al procesar la foto. Intente nuevamente.", null);
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        // Clear any existing keyboard
        handler.getChatData().get(chatId).setReplyKeyboard(null);

        handler.reply(chatId,
                "📸 Perfecto! Ahora puede enviar las fotos de la carta.\n\n" +
                        "• Envíe las fotos una por una\n" +
                        "• Máximo 10 fotos por publicación\n" +
                        "• Escriba 'continuar' cuando termine\n" +
                        "• Escriba 'omitir' si no desea enviar fotos", null);
    }

    private void handleContinueCommand(long chatId, ResponseHandler handler) {
        int photoCount = handler.getChatData().get(chatId).getImages().size();
        if (photoCount > 0) {
            handler.reply(chatId,
                    String.format("✅ Perfecto! Se guardaron %d foto(s).", photoCount), null);
        } else {
            handler.reply(chatId, "✅ Continuando sin fotos.", null);
        }
        // Don't handle transition here - ResponseHandler will do it
    }

    private void handleSkipCommand(long chatId, ResponseHandler handler) {
        // Clear any photos that might have been added
        handler.getChatData().get(chatId).getImages().clear();
        handler.reply(chatId, "✅ Se omitió la carga de fotos.", null);
        // Don't handle transition here - ResponseHandler will do it
    }

    private String downloadAndEncodeImage(PhotoSize photo, AbsSender absSender) throws TelegramApiException, IOException {
        // Get file path from Telegram
        GetFile getFile = new GetFile();
        getFile.setFileId(photo.getFileId());
        File filePath = absSender.execute(getFile);

        // Construct download URL
        String imageUrl = "https://api.telegram.org/file/bot" + botToken + "/" + filePath.getFilePath();

        // Download and encode
        try (InputStream in = new URL(imageUrl).openStream()) {
            byte[] buffer = in.readAllBytes();
            return java.util.Base64.getEncoder().encodeToString(buffer);
        }
    }
}