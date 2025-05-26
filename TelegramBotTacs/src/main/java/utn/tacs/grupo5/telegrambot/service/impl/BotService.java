package utn.tacs.grupo5.telegrambot.service.impl;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.dto.*;
import utn.tacs.grupo5.telegrambot.service.IBotService;
import utn.tacs.grupo5.telegrambot.util.JwtUtil;
import utn.tacs.grupo5.telegrambot.util.PasswordVerifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;


@Service
public class BotService implements IBotService {
    private final OfferService offerService;
    private GameService gameService;
    private AutService autService;
    private UserService userService;
    private CardService cardService;
    private PostService postService;

    public BotService(GameService gameService,
                      AutService autService,
                      UserService userService,
                      CardService cardService,
                      PostService postService, OfferService offerService) {
        this.gameService = gameService;
        this.autService = autService;
        this.userService = userService;
        this.cardService = cardService;
        this.postService = postService;
        this.offerService = offerService;
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
        List<String> wantedCardsIds = chatData.getSelectedCardsIds();
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



    public List<PostInputDTO> getPosts(String cardName, String gameId, String state) {
        return postService.getPosts(cardName, gameId, state);
    }

    public String createOffer(ChatData chatData) {
        String money = chatData.getEstimatedValue() != null? chatData.getEstimatedValue().toString() : null;
        String postId = chatData.getPublicationId();
        String userId = chatData.getUserId();
        List<OfferedCardsOutputDTO> offeredCards = new ArrayList<>();
        for (int i = 0; i < chatData.getSelectedCardsIds().size(); i++) {
            OfferedCardsOutputDTO offeredCard = OfferedCardsOutputDTO.builder()
                    .cardId(chatData.getSelectedCardsIds().get(i))
                    .conservationStatus(chatData.getSelectedCardsStates().get(i).toUpperCase())
                    .build();
            offeredCards.add(offeredCard);
        }
        OfferOutputDTO offer = OfferOutputDTO.builder()
                .money(money)
                .postId(postId)
                .offererId(userId)
                .offeredCards(offeredCards)
                .build();

        return offerService.createOffer(offer);
    }
}
