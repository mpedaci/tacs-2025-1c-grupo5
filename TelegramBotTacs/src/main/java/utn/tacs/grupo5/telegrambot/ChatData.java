package utn.tacs.grupo5.telegrambot;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

@Data
public class ChatData implements Serializable {
    ReplyKeyboard replyKeyboard;
    private String gameId;
    private String userId;
    private List<String> images = new ArrayList<>();
    private String cardId;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<String> wantedCardIds = new ArrayList<>();;
    private String description;
    private String helpStringValue;
    private String flow;
    private String publicationId;
    private List<CardOutputDTO> currentCards;
    private int currentIndex;
    private boolean needsMoreCardSelection = false;
    private boolean shouldCollectPhotos = false;
    private CardSelectionContext cardSelectionContext;
    private boolean photoCollectionCompleted = false;
    private boolean choosingAnotherCard = false;

    public boolean needsMoreCardSelection() {
        return needsMoreCardSelection;
    }

    public boolean shouldCollectPhotos() {
        return shouldCollectPhotos;
    }

    public void addWantedCard(String cardId) {
        this.wantedCardIds.add(cardId);
    }

    public void clearFlowData() {
        // Limpiar datos del flujo de post
        this.gameId = null;
        this.cardId = null;
        this.conservationStatus = null;
        this.images = new ArrayList<>();
        this.description = null;
        this.wantedCardIds = new ArrayList<>();
        this.estimatedValue = null;
        this.currentCards = new ArrayList<>();
        this.helpStringValue = null;
        this.shouldCollectPhotos = false;
        this.photoCollectionCompleted = false;

        // Limpiar keyboard actual
        this.replyKeyboard = null;
    }

}
