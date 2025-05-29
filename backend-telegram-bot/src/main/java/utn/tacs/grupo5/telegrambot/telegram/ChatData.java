package utn.tacs.grupo5.telegrambot.telegram;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import utn.tacs.grupo5.telegrambot.dto.ConservationStatus;
import utn.tacs.grupo5.telegrambot.dto.card.CardOutputDto;
import utn.tacs.grupo5.telegrambot.dto.post.PostOutputDto;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Data
public class ChatData implements Serializable {
    ReplyKeyboard replyKeyboard;
    private UUID gameId;
    private UUID userId;
    private List<String> images = new ArrayList<>();
    private UUID cardId;
    private String cardName;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<UUID> selectedCardsIds = new ArrayList<>();
    private List<String> selectedCardsNames = new ArrayList<>();
    private List<ConservationStatus> selectedCardsStates = new ArrayList<>();
    private String description;
    private String helpStringValue;
    private String flow;
    private UUID publicationId;
    private List<CardOutputDto> currentCards;
    private int currentIndex;
    private boolean needsMoreCardSelection = false;
    private boolean shouldCollectPhotos = false;
    private CardSelectionContext cardSelectionContext;
    private boolean photoCollectionCompleted = false;
    private boolean choosingAnotherCard = false;
    private List<PostOutputDto> currentPost;
    private String token;

    public boolean needsMoreCardSelection() {
        return needsMoreCardSelection;
    }

    public boolean shouldCollectPhotos() {
        return shouldCollectPhotos;
    }

    public void addWantedCard(UUID cardId, String cardName) {
        this.selectedCardsIds.add(cardId);
        this.selectedCardsNames.add(cardName);
    }

    public void clearFlowData() {
        // Limpiar datos del flujo de post
        this.cardId = null;
        this.cardName = null;
        this.conservationStatus = null;
        this.images = new ArrayList<>();
        this.description = null;
        this.selectedCardsIds = new ArrayList<>();
        this.selectedCardsNames = new ArrayList<>();
        this.selectedCardsStates = new ArrayList<>();
        this.estimatedValue = null;
        this.currentCards = new ArrayList<>();
        this.helpStringValue = null;
        this.shouldCollectPhotos = false;
        this.photoCollectionCompleted = false;
        this.gameId = null;
        this.flow = null;
        this.publicationId = null;
        this.currentIndex = 0;
        this.needsMoreCardSelection = false;
        this.cardSelectionContext = null;
        this.choosingAnotherCard = false;

        // Limpiar keyboard actual
        this.replyKeyboard = null;
    }

}
