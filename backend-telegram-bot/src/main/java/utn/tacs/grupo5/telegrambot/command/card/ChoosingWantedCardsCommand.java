package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;
import utn.tacs.grupo5.telegrambot.telegram.CardSelectionContext;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;
import java.util.UUID;

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
            chatData.setCurrentIndex(0);
        }else if ("Otra".equals(messageText)) {
            chatData.setNeedsMoreCardSelection(false);
            chatData.setChoosingAnotherCard(true);
            chatData.setHelpStringValue("Cartas");
        } else if (isCardSelection(messageText)) {
            chatData.setCurrentIndex(Integer.parseInt(message.getText()));
            handler.reply(chatId,"Elija el estado de conservacion", KeyboardFactory.getCardConditionOption());
        }else if (matchesConservationStatus(messageText)){
            ConservationStatus conservationStatus = ConservationStatus.fromString(messageText);
            chatData.getSelectedCardsStates().add(conservationStatus);
            selectWantedCard(chatId,String.valueOf(chatData.getCurrentIndex()), handler, chatData);
        } else {
            throw new BotException("Seleccione un número de carta, 'Mas' para ver más opciones, o 'Finalizar' para terminar.");
        }
    }

    private void showMoreCards(long chatId, ResponseHandler handler, ChatData chatData) {
        int currentIndex = chatData.getCurrentIndex();
        List<CardOutputDto> allCards = chatData.getCurrentCards();

        if (currentIndex >= allCards.size()) {
            handler.reply(chatId, "No hay más cartas disponibles", KeyboardFactory.getMore());
            return;
        }

        int endIndex = Math.min(currentIndex + CARDS_PER_PAGE, allCards.size());
        List<CardOutputDto> cardsToShow = allCards.subList(currentIndex, endIndex);

        for (int i = 0; i < cardsToShow.size(); i++) {
            CardOutputDto card = cardsToShow.get(i);
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

        handler.reply(chatId, "¿Más cartas?", KeyboardFactory.getMore());
    }

    private void selectWantedCard(long chatId, String messageText, ResponseHandler handler, ChatData chatData) {
        try {
            int selectedIndex = Integer.parseInt(messageText);
            List<CardOutputDto> cards = chatData.getCurrentCards();

            if (selectedIndex < 0 || selectedIndex >= cards.size()) {
                throw new BotException("Número de carta inválido");
            }

            UUID selectedCardId = cards.get(selectedIndex).getId();
            String selectedCardName = cards.get(selectedIndex).getName();

            // Agregar carta a la lista de cartas deseadas
            chatData.addWantedCard(selectedCardId, selectedCardName);

            handler.reply(chatId, "Carta agregada: " + selectedCardName, null);
            handler.reply(chatId,
                    String.format("Cartas seleccionadas: %d. ¿Desea agregar mas cartas?",
                            chatData.getSelectedCardsIds().size()),
                    KeyboardFactory.getOtherOrFinish());
        } catch (NumberFormatException e) {
            throw new BotException("Por favor ingrese un número válido");
        }
    }

    private void finishWantedCardSelection(long chatId, ResponseHandler handler, ChatData chatData) {
        if (chatData.getSelectedCardsIds().isEmpty()) {
            throw new BotException("Debe seleccionar al menos una carta para intercambio");
        }

        chatData.setNeedsMoreCardSelection(false);
        handler.reply(chatId,
                String.format("Selección finalizada. %d cartas agregadas para intercambio.",
                        chatData.getSelectedCardsIds().size()), null);
    }

    private boolean isCardSelection(String text) {
        return text != null && text.matches("\\d+");
    }

    public boolean matchesConservationStatus(String input) {
        for (ConservationStatus status : ConservationStatus.values()) {
            if (status.name().equalsIgnoreCase(input)) {
                return true;
            }
        }
        return false;
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
            handler.reply(chatId, "¿Más cartas o finalizar selección?", KeyboardFactory.getOtherOrFinish());
        }
    }
}