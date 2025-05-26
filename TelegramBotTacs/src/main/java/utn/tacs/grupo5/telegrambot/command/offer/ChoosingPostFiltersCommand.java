package utn.tacs.grupo5.telegrambot.command.offer;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

@Component
public class ChoosingPostFiltersCommand implements StateCommand {
    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        String messageText = message.getText().toLowerCase().trim();

       if ("no".equals(messageText)) {
            // No usar filtros
            chatData.setHelpStringValue("No filters");
            handler.reply(chatId, "Buscando todas las publicaciones sin filtros...", null);
       } else if ("siguiente".equals(messageText)) {
           chatData.setHelpStringValue("Filter");
           return;
       }
       else {
            // Aquí podrías agregar lógica para procesar filtros específicos
            // Por ahora, cualquier otro texto se considera como "saltar filtros"
            throw new BotException("Opción no válida. Use 'Siguiente' para saltar filtros o 'No' para buscar sin filtros.");
       }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId,
                "🔍 **Filtros de Búsqueda**\n\n" +
                        "Para filtrar publicaciones complete los campos o:\n" +
                        "• 'Siguiente' - empezar a elegir filtros\n" +
                        "• 'No' - buscar todas las publicaciones",
                KeyboardFactory.getNextOrNo());
    }
}

