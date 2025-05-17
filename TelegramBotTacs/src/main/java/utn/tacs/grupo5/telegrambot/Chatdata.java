package utn.tacs.grupo5.telegrambot;

import lombok.Data;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import utn.tacs.grupo5.entity.post.ConservationStatus;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.LinkedList;
import java.util.List;
import java.util.UUID;

@Data
public class Chatdata implements Serializable {
    ReplyKeyboard replyKeyboard;
    private UUID user;
    private String game;
    private UUID userId;
    private List<String> images;
    private UUID cardId;
    private ConservationStatus conservationStatus;
    private BigDecimal estimatedValue;
    private List<UUID> wantedCardsIds;
    private String description;
    private String helpStringValue;
    private String flow;
    private UUID publicationId;

    public Chatdata() {
        this.images = new LinkedList<>();
        this.wantedCardsIds = new LinkedList<>();
    }
}
