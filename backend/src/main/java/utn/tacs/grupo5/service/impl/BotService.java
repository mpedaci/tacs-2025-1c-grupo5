package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import utn.tacs.grupo5.bot.Chatdata;
import utn.tacs.grupo5.bot.handler.ResponseHandler;
import utn.tacs.grupo5.bot.handler.exception.*;
import utn.tacs.grupo5.dto.auth.AuthInputDto;
import utn.tacs.grupo5.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.repository.impl.InMemoryGameRepository;
import utn.tacs.grupo5.security.JwtUtil;
import utn.tacs.grupo5.service.IBotService;
import utn.tacs.grupo5.util.PasswordVerifier;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
public class BotService implements IBotService {

    private AuthService authService;
    UserService userService;
    CardService cardService;
    PostService postService;
    InMemoryGameRepository gameRepository;
    JwtUtil jwtUtil;

    public BotService(AuthService authService,
                      UserService userService,
                      CardService cardService,
                      PostService postService,
                      InMemoryGameRepository gameRepository,
                      JwtUtil jwtUtil) {
        this.authService = authService;
        this.userService = userService;
        this.cardService = cardService;
        this.postService = postService;
        this.gameRepository = gameRepository;
        this.jwtUtil = jwtUtil;
    }

    public boolean findExistingUsername(String text) {
        return userService.findByUsername(text).isPresent();
    }

    public Optional<User> findUser(String text) throws BotException {
        String[] string = text.split(", ", 2);
        if (string.length < 2) {
            throw new BadUserInputException("Formato inválido. \nUse: usuario, contraseña");
        }

        String username = string[0].trim();
        String password = string[1].trim();

        AuthInputDto authInputDto = new AuthInputDto(username, password);
        AuthOutputDto token = authService.login(authInputDto);
        String user = jwtUtil.getUsernameFromToken(token.getToken());

        return userService.findByUsername(user)
                .or(() -> { throw new NotFoundException("Usuario no encontrado tras autenticación."); });
    }

    public void registerUser(String text) throws BotException {
        String[] string = text.split(", ", 3);
        if (string.length < 3) {
            throw new BadUserInputException("Formato inválido. \nUse: nombre, username, password");
        }

        String name = string[0].trim();
        String username = string[1].trim();
        String password = string[2].trim();

        if (findExistingUsername(username)) {
            throw new InvalidUsernameException("El nombre de usuario ya existe.");
        }

        try {
            PasswordVerifier.validatePassword(password);
        } catch (InvalidPasswordException e) {
            throw new InvalidPasswordException("La contraseña no es válida. \n" + e.getMessage());
        }

        UserInputDto userInputDto = new UserInputDto(name, username, password, false);
        userService.save(userInputDto);
    }

    public Card findCard(String game, String cardName) throws BotException {
        UUID gameId = gameRepository.findByName(game)
                .orElseThrow(() -> new NotFoundException("Juego no encontrado."))
                .getId();
        return cardService.getAllByGameId(gameId, cardName).stream().findFirst()
                .orElseThrow(() -> new NotFoundException("Carta: " + cardName + " del juego: " + game + " no encontrada"));

    }

    public List<String> savePhotos(List<PhotoSize> photos) { //TODO implementar
        return photos.stream()
                .map(photo -> {
                    String fileId = photo.getFileId();
                    String filePath = photo.getFilePath();
                    return fileId + "," + filePath;
                })
                .toList();
    }

    public void saveCardValue(String text, Chatdata chatdata, String game, String helpStringValue) throws BotException {
        chatdata.setWantedCardsIds(new ArrayList<>());
        switch (helpStringValue) {
            case "Ambos" -> {
                String[] string = text.split("\\n");
                String money = string[0].trim();
                String[] cards = string[1].split(", ");
                chatdata.setEstimatedValue(BigDecimal.valueOf(Long.parseLong(money)));
                for (String card : cards) {
                    Card validCard = findCard(game, card);
                    chatdata.getWantedCardsIds().add(validCard.getId());
                }
            }
            case "Dinero" -> {
                String money = text.trim();
                chatdata.setEstimatedValue(new BigDecimal(money));
            }
            case "Cartas" -> {
                String[] cards = text.split(", ");
                for (String card : cards) {
                    Card validCard = findCard(game, card);
                    chatdata.getWantedCardsIds().add(validCard.getId());
                }
            }
        }
    }

    public UUID createPost(PostInputDto postInputDto) throws BotException{
        try {
            return postService.save(postInputDto).getId();
        }catch (Exception e) {
            throw new BotException("Error al crear la publicación. \n" + e.getMessage());
        }
    }
}
