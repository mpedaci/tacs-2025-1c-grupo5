package utn.tacs.grupo5.service.impl;

import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
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

        validatePassword(password);

        UserInputDto userInputDto = new UserInputDto(name, username, password, false);
        userService.save(userInputDto);
    }

    private void validatePassword(String password) { //TODO esto es un util no va aca
        if (password.length() < 8) {
            throw new InvalidPasswordException("La contraseña debe tener al menos 8 caracteres.");
        }
        if (!password.matches(".*[A-Z].*")) {
            throw new InvalidPasswordException("La contraseña debe contener al menos una letra mayúscula.");
        }
        if (!password.matches(".*[a-z].*")) {
            throw new InvalidPasswordException("La contraseña debe contener al menos una letra minúscula.");
        }
        if (!password.matches(".*\\d.*")) {
            throw new InvalidPasswordException("La contraseña debe contener al menos un número.");
        }
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

    public void saveValue(String text, PostInputDto postInputDto,String game, String helpStringValue) throws BotException {
        postInputDto.setWantedCardsIds(new ArrayList<>());
        switch (helpStringValue) {
            case "Ambos" -> {
                String[] string = text.split("\\n");
                String money = string[0].trim();
                String[] cards = string[1].split(", ");
                postInputDto.setEstimatedValue(BigDecimal.valueOf(Long.parseLong(money)));
                for (String card : cards) {
                    Card validCard = findCard(game, card);
                    postInputDto.getWantedCardsIds().add(validCard.getId());
                }
            }
            case "Dinero" -> {
                String money = text.trim();
                postInputDto.setEstimatedValue(new BigDecimal(money));
            }
            case "Cartas" -> {
                String[] cards = text.split(", ");
                for (String card : cards) {
                    Card validCard = findCard(game, card);
                    postInputDto.getWantedCardsIds().add(validCard.getId());
                }
            }
        }
    }

    public void createPost(PostInputDto postInputDto) {
        postService.save(postInputDto);
    }
}
