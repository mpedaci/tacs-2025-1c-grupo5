package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.CardSelectionContext;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;

/**
 * Command specifically for choosing wanted cards in exchange
 */
@Component
public class ChoosingWantedCardsCommand implements StateCommand {
    private static final int CARDS_PER_PAGE = 5;

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        String messageText = message.getText();

        if ("Mas".equals(messageText)) {
            showMoreCards(chatId, handler, chatData);
        } else if ("Finalizar".equals(messageText)) {
            finishWantedCardSelection(chatId, handler, chatData);
        } else if (isCardSelection(messageText)) {
            selectWantedCard(chatId, messageText, handler, chatData);
        } else {
            throw new BotException("Seleccione un número de carta, 'Mas' para ver más opciones, o 'Finalizar' para terminar.");
        }
    }

    private void showMoreCards(long chatId, ResponseHandler handler, ChatData chatData) {
        int currentIndex = chatData.getCurrentIndex();
        List<CardOutputDTO> allCards = chatData.getCurrentCards();

        if (currentIndex >= allCards.size()) {
            handler.reply(chatId, "No hay más cartas disponibles", KeyboardFactory.getMoreOrFinish());
            return;
        }

        int endIndex = Math.min(currentIndex + CARDS_PER_PAGE, allCards.size());
        List<CardOutputDTO> cardsToShow = allCards.subList(currentIndex, endIndex);

        for (int i = 0; i < cardsToShow.size(); i++) {
            CardOutputDTO card = cardsToShow.get(i);
            int cardNumber = currentIndex + i;
            handler.replyWithPhoto(chatId,
                    String.format("%d - %s", cardNumber, card.getName()),
                    card.getImageUrl());
        }

        chatData.setCurrentIndex(endIndex);

        // Check if we need more selection
        if (endIndex < allCards.size()) {
            chatData.setNeedsMoreCardSelection(true);
        } else {
            chatData.setNeedsMoreCardSelection(false);
        }

        handler.reply(chatId, "¿Más cartas o finalizar selección?", KeyboardFactory.getMoreOrFinish());
    }

    private void selectWantedCard(long chatId, String messageText, ResponseHandler handler, ChatData chatData) {
        try {
            int selectedIndex = Integer.parseInt(messageText);
            List<CardOutputDTO> cards = chatData.getCurrentCards();

            if (selectedIndex < 0 || selectedIndex >= cards.size()) {
                throw new BotException("Número de carta inválido");
            }

            String selectedCardId = cards.get(selectedIndex).getId();
            String selectedCardName = cards.get(selectedIndex).getName();

            // Agregar carta a la lista de cartas deseadas
            chatData.addWantedCard(selectedCardId);

            handler.reply(chatId, "Carta agregada: " + selectedCardName, null);
            handler.reply(chatId,
                    String.format("Cartas seleccionadas: %d. ¿Desea agregar más cartas?",
                            chatData.getWantedCardIds().size()),
                    KeyboardFactory.getMoreOrFinish());

        } catch (NumberFormatException e) {
            throw new BotException("Por favor ingrese un número válido");
        }
    }

    private void finishWantedCardSelection(long chatId, ResponseHandler handler, ChatData chatData) {
        if (chatData.getWantedCardIds().isEmpty()) {
            throw new BotException("Debe seleccionar al menos una carta para intercambio");
        }

        chatData.setNeedsMoreCardSelection(false);
        handler.reply(chatId,
                String.format("Selección finalizada. %d cartas agregadas para intercambio.",
                        chatData.getWantedCardIds().size()), null);
    }

    private boolean isCardSelection(String text) {
        return text != null && text.matches("\\d+");
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);

        // Verificar que tengamos cartas para mostrar
        if (chatData.getCurrentCards() == null || chatData.getCurrentCards().isEmpty()) {
            handler.reply(chatId, "Error: No hay cartas disponibles para seleccionar", null);
            return;
        }

        // Establecer el contexto correcto
        chatData.setCardSelectionContext(CardSelectionContext.CHOOSING_WANTED_CARDS);

        if (chatData.getCurrentIndex() == 0) {
            handler.reply(chatId, "Selecciona las cartas que te interesan para intercambio:", null);
            showMoreCards(chatId, handler, chatData);
        } else {
            handler.reply(chatId, "¿Más cartas o finalizar selección?", KeyboardFactory.getMoreOrFinish());
        }
    }
}