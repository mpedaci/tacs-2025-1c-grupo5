package utn.tacs.grupo5.telegrambot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.dto.PostCreationDTO;
import utn.tacs.grupo5.telegrambot.dto.CardOutputDTO;
import utn.tacs.grupo5.telegrambot.dto.PostInputDTO;
import utn.tacs.grupo5.telegrambot.dto.RegisterOutputDTO;
import utn.tacs.grupo5.telegrambot.service.IBotService;
import utn.tacs.grupo5.telegrambot.util.JwtUtil;
import utn.tacs.grupo5.telegrambot.util.PasswordVerifier;

import java.math.BigDecimal;
import java.util.List;


@Service
public class BotService implements IBotService {
    private GameService gameService;
    private AutService autService;
    private UserService userService;
    private CardService cardService;
    private PostService postService;

    public BotService(GameService gameService,
                      AutService autService,
                      UserService userService,
                      CardService cardService,
                      PostService postService) {
        this.gameService = gameService;
        this.autService = autService;
        this.userService = userService;
        this.cardService = cardService;
        this.postService = postService;
    }

    public String logInUser(String username, String password) {
        try {
            String responseJson = autService.logInUser(username, password);
            ObjectMapper mapper = new ObjectMapper();
            String jwt = mapper.readTree(responseJson).get("token").asText();
            return (String) JwtUtil.decodePayload(jwt).get("jti");
        } catch (Exception e) {
            throw new RuntimeException("Error al procesar el token JWT", e);
        }
    }

    public RegisterOutputDTO registerUser(String name, String username, String password) {
        PasswordVerifier.validatePassword(password);
        return  userService.registerUser(name, username, password);
    }

    public String findGame(String gameName) {
        return gameService.findGame(gameName);
    }

    public List<CardOutputDTO> findCard(String game, String cardName) {
        return cardService.findCard(game, cardName);
    }

    public String createPost(ChatData chatData) {
        String userId = chatData.getUserId();
        String cardId = chatData.getCardId();
        String conservationStatus = String.valueOf(chatData.getConservationStatus());
        List<String> images = chatData.getImages();
        String description = chatData.getDescription();
        List<String> wantedCardsIds = chatData.getWantedCardIds();
        BigDecimal estimatedValue = chatData.getEstimatedValue();

        PostCreationDTO post = PostCreationDTO.builder()
                .userId(userId)
                .cardId(cardId)
                .conservationStatus(conservationStatus)
                .images(images)
                .description(description)
                .wantedCardsIds(wantedCardsIds)
                .estimatedValue(estimatedValue)
                .build();

        return postService.createPost(post);
    }

    public void saveCardValue(String text, ChatData chatData) {
        if (text == null || text.isBlank()) {
            throw new IllegalArgumentException("El texto no puede estar vacío.");
        }

        // Solo procesar valores monetarios - las cartas se manejan por selección individual
        if (isNumeric(text.trim())) {
            chatData.setEstimatedValue(new BigDecimal(text.trim()));
        } else {
            throw new IllegalArgumentException("Solo se permiten valores numéricos. Use la selección individual para cartas.");
        }
    }

    private boolean isNumeric(String str) {
        try {
            new BigDecimal(str);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public List<PostInputDTO> getPosts(String cardName, String gameId, String state) {
        return postService.getPosts(cardName, gameId, state);
    }
}
