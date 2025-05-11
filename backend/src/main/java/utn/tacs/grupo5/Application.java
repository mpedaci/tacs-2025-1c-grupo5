package utn.tacs.grupo5;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ConfigurableApplicationContext;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;
import utn.tacs.grupo5.dto.user.UserInputDto;
import utn.tacs.grupo5.service.impl.UserService;

@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        ConfigurableApplicationContext ctx = SpringApplication.run(Application.class, args);
        try {
            TelegramBotsApi botsApi = new TelegramBotsApi(DefaultBotSession.class);
            UserService userService = ctx.getBean(UserService.class);
            UserInputDto inputDto = new UserInputDto();
            inputDto.setUsername("jaime");
            inputDto.setPassword("jaime");
            userService.save(inputDto);
            botsApi.registerBot(ctx.getBean("telegramBot", AbilityBot.class));
        } catch (TelegramApiException e) {
            throw new RuntimeException(e);
        }
    }
}
