package utn.tacs.grupo5.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import utn.tacs.grupo5.bot.Constants;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.service.impl.BotService;

import java.util.Map;
import java.util.Optional;

import static utn.tacs.grupo5.bot.Constants.START_TEXT;
import static utn.tacs.grupo5.bot.UserState.*;

@Component
public class ResponseHandler {
    private SilentSender sender;
    private Map<Long, UserState> chatStates;
    private BotService botService;

    public ResponseHandler(BotService botservice){
        this.botService = botservice;
    }

    public void init(SilentSender sender, DBContext db) {
        this.sender = sender;
        chatStates = db.getMap(Constants.CHAT_STATES);
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }

    public void replyToStart(long chatId) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(START_TEXT);
        sender.execute(message);
        chatStates.put(chatId, AWAITING_LOG_IN);
    }

    public void replyToButtons(long chatId, Message message) {
        if (message.getText().equalsIgnoreCase("/stop")) {
            stopChat(chatId);
        }
        switch (message.getChatId()) {
            case AWAITING_LOG_IN: replyToUser(message.getChatId(), message);
            default:
        }
    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("exiting");
        chatStates.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }

    private void replyToUser(long chatId, Message message){
        Optional<User> user = botService.searchForUser();
        if (user.isPresent()){
            //hello user
        }
        else;//not a valid user try again
    }
}