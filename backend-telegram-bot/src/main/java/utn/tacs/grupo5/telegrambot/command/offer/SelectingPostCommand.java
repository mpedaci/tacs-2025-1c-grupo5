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
public class SelectingPostCommand implements StateCommand {
    private static final int POSTS_PER_PAGE = 3;

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        String messageText = message.getText().trim();

        if ("Mas".equals(messageText)) {
            showMorePosts(chatId, handler, chatData);
        } else if (isPostSelection(messageText)) {
            selectPost(chatId, messageText, handler, chatData);
        } else {
            throw new BotException("Opción no válida. Seleccione un número de publicación o 'Mas' para ver más opciones.");
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        List<PostInputDTO> posts = chatData.getCurrentPost();

        if (posts == null || posts.isEmpty()) {
            handler.reply(chatId, "❌ No hay publicaciones disponibles.", null);
            return;
        }

        chatData.setCurrentIndex(0); // Reset index
        handler.reply(chatId, "📋 **Selección de Publicación**\n\nSeleccione la publicación para hacer una oferta:", null);
        showMorePosts(chatId, handler, chatData);
    }

    private void showMorePosts(long chatId, ResponseHandler handler, ChatData chatData) {
        List<PostInputDTO> allPosts = chatData.getCurrentPost();
        int currentIndex = chatData.getCurrentIndex();

        if (currentIndex >= allPosts.size()) {
            handler.reply(chatId, "No hay más publicaciones disponibles", null);
            return;
        }

        int endIndex = Math.min(currentIndex + POSTS_PER_PAGE, allPosts.size());
        List<PostInputDTO> postsToShow = allPosts.subList(currentIndex, endIndex);

        StringBuilder message = new StringBuilder("📋 **Publicaciones disponibles:**\n\n");

        for (int i = 0; i < postsToShow.size(); i++) {
            PostInputDTO post = postsToShow.get(i);
            int postNumber = currentIndex + i;

            message.append(String.format("**%d** - %s\n", postNumber, post.getCard().getName()));
            message.append(String.format("🎮 Juego: %s\n", post.getCard().getGame().getTitle()));
            message.append(String.format("💰 Valor: %s\n", post.getEstimatedValue()));
            message.append(String.format("📝 Estado: %s\n", post.getConservationStatus()));
            if (post.getDescription() != null && !post.getDescription().trim().isEmpty()) {
                message.append(String.format("ℹ️ Descripción: %s\n", post.getDescription()));
            }
            message.append("─────────────\n");
        }

        handler.reply(chatId, message.toString(), null);
        chatData.setCurrentIndex(endIndex);

        // Mostrar opciones
        if (endIndex < allPosts.size()) {
            handler.reply(chatId, "Seleccione el número de la publicación o 'Mas' para ver más:",
                    KeyboardFactory.getMore());
        } else {
            handler.reply(chatId, "Seleccione el número de la publicación:", null);
        }
    }

    private void selectPost(long chatId, String messageText, ResponseHandler handler, ChatData chatData) {
        try {
            int selectedIndex = Integer.parseInt(messageText);
            List<PostInputDTO> posts = chatData.getCurrentPost();

            if (selectedIndex < 0 || selectedIndex >= posts.size()) {
                throw new BotException("Número de publicación inválido");
            }

            PostInputDTO selectedPost = posts.get(selectedIndex);
            chatData.setPublicationId(selectedPost.getId());

            handler.reply(chatId,
                    String.format("✅ **Publicación seleccionada:**\n\n" +
                                    "📋 %s\n" +
                                    "🎮 Juego: %s\n" +
                                    "💰 Valor: %s\n" +
                                    "📝 Estado: %s\n\n" +
                                    "Ahora seleccione lo que desea ofrecer a cambio...",
                            selectedPost.getCard().getName(),
                            selectedPost.getCard().getGame().getTitle(),
                            selectedPost.getEstimatedValue(),
                            selectedPost.getConservationStatus()), null);

            chatData.setGameId(selectedPost.getCard().getGame().getId());

        } catch (NumberFormatException e) {
            throw new BotException("Por favor ingrese un número válido");
        }
    }

    private boolean isPostSelection(String text) {
        return text != null && text.matches("\\d+");
    }
}