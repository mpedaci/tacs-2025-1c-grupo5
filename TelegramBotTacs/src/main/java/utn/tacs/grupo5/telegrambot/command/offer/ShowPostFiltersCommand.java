package utn.tacs.grupo5.telegrambot.command.offer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.PostInputDTO;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;

@Component
public class ShowPostFiltersCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        // Este command se ejecuta automáticamente al entrar
        // No necesita procesar mensajes del usuario
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);

        try {
            handler.reply(chatId, "🔍 Buscando publicaciones...", null);

            String gameId = chatData.getGameId();
            String stateOfCard = chatData.getConservationStatus() != null ?
                    chatData.getConservationStatus().toString() : null;
            String cardName = chatData.getCardName();

            // Buscar publicaciones
            List<PostInputDTO> posts = handler.getBotService().getPosts(cardName, gameId, stateOfCard);
            chatData.setCurrentPost(posts);

            if (posts.isEmpty()) {
                handler.reply(chatId,
                        "❌ No se encontraron publicaciones con los filtros seleccionados.\n" +
                                "Intente con filtros diferentes.", null);
                return;
            }

            // Mostrar resumen de resultados
            handler.reply(chatId,
                    String.format("✅ Se encontraron **%d publicaciones** con los filtros seleccionados.\n" +
                            "Preparando lista para selección....", posts.size()), null);

            handler.reply(chatId, "Continuar o cancelar?", KeyboardFactory.getContinueOrCancel());
        } catch (Exception e) {
            throw new BotException("Error al buscar publicaciones: " + e.getMessage());
        }
    }
}