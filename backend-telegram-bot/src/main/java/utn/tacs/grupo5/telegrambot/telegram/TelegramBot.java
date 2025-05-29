package utn.tacs.grupo5.telegrambot.telegram;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.bot.AbilityBot;
import org.telegram.abilitybots.api.bot.BaseAbilityBot;
import org.telegram.abilitybots.api.objects.Ability;
import org.telegram.abilitybots.api.objects.Reply;
import org.telegram.telegrambots.meta.api.objects.Update;
import utn.tacs.grupo5.telegrambot.handler.ResponseHandler;

import java.util.function.BiConsumer;

import static org.telegram.abilitybots.api.objects.Locality.USER;
import static org.telegram.abilitybots.api.objects.Privacy.PUBLIC;
import static org.telegram.abilitybots.api.util.AbilityUtils.getChatId;


@Component
public class TelegramBot extends AbilityBot {
    private final Long botCreatorId;
    private final ResponseHandler responseHandler;
    //private final AbsSender absSender;

    public TelegramBot (
            @Value("${TELEGRAM_BOT_TOKEN}") String botToken,
            @Value("${TELEGRAM_BOT_NAME}") String botUsername,
            @Value("${TELEGRAM_CREATOR_ID}")Long botCreatorId,
            ResponseHandler responseHandler) {
        super(botToken, botUsername);
        this.botCreatorId = botCreatorId;
        this.responseHandler = responseHandler;
        responseHandler.init(silent, db, this);
    }

    @Override
    public long creatorId() {
        return botCreatorId;
    }

    public Ability startBot() {
        db.getMap(Constants.CHAT_STATES).clear(); //TODO eliminar esto despues de testear
        db.getMap(Constants.CHAT_DATA).clear();
        return Ability
                .builder()
                .name("start")
                .info(Constants.START_DESCRIPTION)
                .locality(USER)
                .privacy(PUBLIC)
                .action(ctx -> {
                    responseHandler.replyToStart(ctx.chatId());
                })
                .build();
    }

    public Reply replyToMessages() {
        BiConsumer<BaseAbilityBot, Update> action = (bot, upd) -> {
            if (upd.getMessage().hasText()) {
                responseHandler.replyToChat(getChatId(upd), upd.getMessage());
            } else if (upd.getMessage().hasPhoto()) {
                responseHandler.replyToPhoto(getChatId(upd), upd.getMessage().getPhoto());
            }
        };

        return Reply.of(
                action,
                upd -> responseHandler.userIsActive(getChatId(upd)) &&
                        upd.getMessage() != null &&
                        (upd.getMessage().hasText() || upd.getMessage().hasPhoto())
        );
    }
}