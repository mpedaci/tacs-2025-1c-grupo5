package utn.tacs.grupo5.telegrambot.command.post;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.CardSelectionContext;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.math.BigDecimal;
import java.util.List;

/**
 * Command for handling choosing value state - Individual selection only
 */
@Component
public class ChoosingValueCommand implements StateCommand {

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        String helpStringValue = chatData.getHelpStringValue();
        String input = message.getText().trim();

        try {
            if ("Dinero".equals(helpStringValue)) {
                // Solo procesar monto
                processMoney(input, chatData);

            } else if ("Cartas".equals(helpStringValue)) {
                // Buscar cartas para selección individual
                setupCardSelection(input, chatData, handler);

            } else if ("Ambos".equals(helpStringValue)) {
                // Para "Ambos" esperamos primero el monto, luego nombre de carta
                processMoneyAndSetupCards(input, chatData, handler);
            }

        } catch (Exception e) {
            throw new BotException("Error al procesar: " + e.getMessage());
        }
    }

    private void processMoney(String input, ChatData chatData) {
        if (!isNumeric(input)) {
            throw new BotException("El monto ingresado no es válido.");
        }
        chatData.setEstimatedValue(new BigDecimal(input));
    }

    private void setupCardSelection(String cardName, ChatData chatData, ResponseHandler handler) {
        List<CardOutputDTO> cards = handler.getBotService().findCard(chatData.getGameId(), cardName);

        if (cards.isEmpty()) {
            throw new BotException("No se encontraron cartas con el nombre: " + cardName);
        }

        // Configurar para selección individual de cartas deseadas
        chatData.setCurrentCards(cards);
        chatData.setCurrentIndex(0);
        chatData.setCardSelectionContext(CardSelectionContext.CHOOSING_WANTED_CARDS);
        chatData.setNeedsMoreCardSelection(true);
    }

    private void processMoneyAndSetupCards(String input, ChatData chatData, ResponseHandler handler) {
        String[] lines = input.split("\\r?\\n");

        if (lines.length != 2) {
            throw new BotException("Para 'Ambos' debe ingresar el monto en la primera línea y el nombre de carta en la segunda línea.");
        }

        String montoStr = lines[0].trim();
        String cardName = lines[1].trim();

        if (!isNumeric(montoStr)) {
            throw new BotException("El monto ingresado no es válido.");
        }

        // Procesar monto
        chatData.setEstimatedValue(new BigDecimal(montoStr));

        // Configurar cartas para selección
        setupCardSelection(cardName, chatData, handler);
    }

    private boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        String caseMoney = "Ingrese el monto:";
        String caseCards = "Ingrese el nombre de la carta para buscar:";
        String caseBoth = "Ingrese el monto en la primera línea y el nombre de carta en la segunda línea:\nEjemplo:\n150\nPikachu";

        ChatData chatData = handler.getChatData().get(chatId);
        switch (chatData.getHelpStringValue()) {
            case "Dinero" -> handler.reply(chatId, caseMoney, null);
            case "Cartas" -> handler.reply(chatId, caseCards, null);
            case "Ambos" -> handler.reply(chatId, caseBoth, null);
        }
    }
}