package utn.tacs.grupo5.telegrambot;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.dto.PostInputDTO;

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
    private String cardName;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<String> selectedCardsIds = new ArrayList<>();
    private List<String> selectedCardsNames = new ArrayList<>(); //porfavor no me critiques, soy solo un bot
    private List<String> selectedCardsStates = new ArrayList<>();
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
    private List<PostInputDTO> currentPost;

    public boolean needsMoreCardSelection() {
        return needsMoreCardSelection;
    }

    public boolean shouldCollectPhotos() {
        return shouldCollectPhotos;
    }

    public void addWantedCard(String cardId, String cardName) {
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
