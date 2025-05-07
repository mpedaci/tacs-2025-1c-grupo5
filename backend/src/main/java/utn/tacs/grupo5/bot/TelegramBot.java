package utn.tacs.grupo5.bot;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Flag;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;
import utn.tacs.grupo5.bot.handler.ResponseHandler;

import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;

@Component
public class TelegramBot extends AbilityBot {


    private final String botToken;
    private final String botUsername;
    private final Long botCreatorId;
    private final ResponseHandler responseHandler;

    public TelegramBot (
            @Value("${telegram.bot.token}") String botToken,
            @Value("${telegram.bot.name}") String botUsername,
            @Value("${telegram.bot.cid}")Long botCreatorId,
            ResponseHandler responseHandler) {
        super(botToken, botUsername);
        this.botToken = botToken;
        this.botUsername = botUsername;
        this.botCreatorId = botCreatorId;
        this.responseHandler = responseHandler;
        responseHandler.init(silent, db);
    }

    @Override
    public long creatorId() {
        return botCreatorId;
    }

    public Ability startBot() {
        return Ability
                .builder()
                .name("start")
                .info(utn.tacs.grupo5.bot.Constants.START_DESCRIPTION)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> responseHandler.replyToStart(ctx.chatId()))
                .build();
    }

    public Reply replyToButtons() {
        BiConsumer<BaseAbilityBot, Update> action =
                (abilityBot, upd) -> responseHandler.replyToButtons(getChatId(upd), upd.getMessage());
        return Reply.of(action, Flag.TEXT, upd -> responseHandler.userIsActive(getChatId(upd)));
    }

}
