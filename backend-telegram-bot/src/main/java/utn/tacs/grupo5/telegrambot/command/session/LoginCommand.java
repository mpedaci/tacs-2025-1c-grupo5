package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientResponseException;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.auth.AuthOutputDto;
import utn.tacs.grupo5.telegrambot.exceptions.NotFoundException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;
import utn.tacs.grupo5.telegrambot.service.IAuthService;
import utn.tacs.grupo5.telegrambot.utils.JwtUtil;

import java.util.UUID;

/**
 * Command for handling login state
 */
@Component
public class LoginCommand implements StateCommand {
    private final IAuthService authService;

    public LoginCommand(IAuthService authService) {
        this.authService = authService;
    }

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        String[] credentials = message.getText().split(",");
        String username = credentials[0].trim();
        String password = credentials[1].trim();
        try {
            AuthOutputDto authOutput = authService.logInUser(username, password);
            String userId = getUsernameFromToken(authOutput.getToken());
            handler.getChatData().get(chatId).setUserId(UUID.fromString(userId));
            handler.getChatData().get(chatId).setToken(authOutput.getToken());
            String stringResponse = "Bienvenido " + username + "!";
            handler.reply(chatId, stringResponse, KeyboardFactory.getCardsOption());
        } catch (WebClientResponseException e) {
            throw new NotFoundException("Usuario " + username + " no encontrado o contraseña incorrecta");
        }
    }

    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "ingrese su usuario y contraseña \n->usuario, contraseña", null);
    }

    private String getUsernameFromToken(String token) {
        return (String) JwtUtil.decodePayload(token).get("jti");
    }
}
