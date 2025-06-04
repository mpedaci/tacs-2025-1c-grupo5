package utn.tacs.grupo5.telegrambot.command.session;

import org.springframework.stereotype.Component;
import org.springframework.web.reactive.function.client.WebClientException;
import org.telegram.telegrambots.meta.api.objects.Message;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.dto.user.UserOutputDto;
import utn.tacs.grupo5.telegrambot.exceptions.BotException;
import utn.tacs.grupo5.telegrambot.factory.KeyboardFactory;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;
import utn.tacs.grupo5.telegrambot.service.IUserService;
import utn.tacs.grupo5.telegrambot.utils.PasswordVerifier;

/**
 * Command for handling registration state
 */
@Component
public class RegisterCommand implements StateCommand {
    private final IUserService userService;

    public RegisterCommand(IUserService userService) {
        this.userService = userService;
    }

    @Override
    public void execute(long chatId, Message message, ResponseHandler handler) {
        try {
            String[] credentials = message.getText().split(",");
            String name = credentials[0].trim();
            String username = credentials[1].trim();
            String password = credentials[2].trim();
            PasswordVerifier.validatePassword(password);
            UserOutputDto user = userService.registerUser(name, username, password);
            handler.getChatData().get(chatId).setUserId(user.getId());
            handler.reply(chatId, "Registrado con éxito", KeyboardFactory.getCardsOption());
        } catch (WebClientException e) {
            throw new BotException("Error al registrar el usuario, intente nuevamente mas tarde");
        }
    }
    @Override
    public void onEnter(long chatId, ResponseHandler handler) {
        handler.reply(chatId, "Ingrese: \n-> nombre, username, password", null);
    }
}
