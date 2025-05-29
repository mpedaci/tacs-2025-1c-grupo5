package utn.tacs.grupo5.telegrambot.command.card;

import org.springframework.stereotype.Component;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import utn.tacs.grupo5.telegrambot.telegram.CardSelectionContext;
import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.List;
import java.util.UUID;

@Component
public class SelectingCardCommand implements StateCommand {
    private static final int CARDS_PER_PAGE = 5;

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);
        String messageText = message.getText();

        if ("Mas".equals(messageText)) {
            showMoreCards(chatId, handler, chatData);
        } else if ("Finalizar".equals(messageText) && isWantedCardsContext(chatData)) {
            finishWantedCardSelection(chatId, handler, chatData);
        } else if (isCardSelection(messageText)) {
            selectCard(chatId, messageText, handler, chatData);
        } else {
            String validOptions = isWantedCardsContext(chatData) ?
                    "Seleccione un número de carta, 'Mas' para ver más opciones, o 'Finalizar' para terminar." :
                    "Seleccione un número de carta o 'Mas' para ver más opciones.";
            throw new BotException("Opción no válida. " + validOptions);
        }
    }

    private void showMoreCards(long chatId, ResponseHandler handler, ChatData chatData) {
        int currentIndex = chatData.getCurrentIndex();
        List<CardOutputDto> allCards = chatData.getCurrentCards();

        if (currentIndex >= allCards.size()) {
            handler.reply(chatId, "No hay más cartas disponibles", null);
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
    }

    private void selectCard(long chatId, String messageText, ResponseHandler handler, ChatData chatData) {
        try {
            int selectedIndex = Integer.parseInt(messageText);
            List<CardOutputDto> cards = chatData.getCurrentCards();

            if (selectedIndex < 0 || selectedIndex >= cards.size()) {
                throw new BotException("Número de carta inválido");
            }

            UUID selectedCardId = cards.get(selectedIndex).getId();
            String selectedCardName = cards.get(selectedIndex).getName();

            if (isOfferedCardContext(chatData)) {
                // Seleccionando la carta que está ofreciendo
                chatData.setCardId(selectedCardId);
                chatData.setNeedsMoreCardSelection(false);
                handler.reply(chatId, "Carta seleccionada: " + selectedCardName, null);

            } else if (isWantedCardsContext(chatData)) {
                // Seleccionando cartas que quiere a cambio
                chatData.setCardId(selectedCardId);
                handler.reply(chatId, "Carta agregada a lista de intercambio: " + selectedCardName, null);
                handler.reply(chatId,
                        String.format("Cartas seleccionadas: %d. ¿Desea agregar más cartas?",
                                chatData.getSelectedCardsIds().size()),
                        KeyboardFactory.getMoreOrFinish());
            }

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

    private boolean isOfferedCardContext(ChatData chatData) {
        return chatData.getCardSelectionContext() == CardSelectionContext.CHOOSING_OFFERED_CARD;
    }

    private boolean isWantedCardsContext(ChatData chatData) {
        return chatData.getCardSelectionContext() == CardSelectionContext.CHOOSING_WANTED_CARDS;
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        ChatData chatData = handler.getChatData().get(chatId);

        if (chatData.getCurrentIndex() == 0) {
            String contextMessage = getContextMessage(chatData);
            handler.reply(chatId, contextMessage, null);
            showMoreCards(chatId, handler, chatData);
        }

        ReplyKeyboard keyboard = isWantedCardsContext(chatData) ?
                KeyboardFactory.getMoreOrFinish() :
                KeyboardFactory.getMore();

        String actionMessage = isWantedCardsContext(chatData) ?
                "¿Más cartas o finalizar selección?" :
                "¿Más cartas?";

        handler.reply(chatId, actionMessage, keyboard);
    }

    private String getContextMessage(ChatData chatData) {
        return switch (chatData.getCardSelectionContext()) {
            case CHOOSING_OFFERED_CARD -> "Selecciona la carta que estás ofreciendo:";
            case CHOOSING_WANTED_CARDS -> "Selecciona las cartas que te interesan para intercambio:";
            default -> "Selecciona una carta:";
        };
    }
}
