package utn.tacs.grupo5.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.User;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import utn.tacs.grupo5.bot.Constants;
import utn.tacs.grupo5.bot.KeyboardFactory;
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

    protected void reply(long chatId, String text, ReplyKeyboard replyKeyboard, UserState userState) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        if (replyKeyboard != null) {
            message.setReplyMarkup(replyKeyboard);
        }
        sender.execute(message);
        chatStates.put(chatId, userState);
    }

    public void replyToStart(long chatId) {
        reply(chatId,START_TEXT,KeyboardFactory.getStartOption(),AWAITING_SESSION);
    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Saliendo");
        chatStates.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }

    public void replyToButtons(long chatId, Message message) {
        if (message.getText().equalsIgnoreCase("/stop")) {
            stopChat(chatId);
        }
        switch (message.getChatId()) {
            case AWAITING_SESSION: replyToStartSession(message.getChatId(), message);
            default:
        }
    }

    private void replyToStartSession(long chatId, Message message){
        if ("Log In".equalsIgnoreCase(message.getText())){
            Optional<User> user = botService.findUser();
            if (user.isPresent()){
                String stringResponse = "Bienvenido " + user.get().getFirstName() + " " + user.get().getLastName();
                reply(chatId, stringResponse, KeyboardFactory.getCardsOption(), CHOOSING_OPTIONS);
            }
            else {
                String stringResponse = "Usuario no encontrado, vuelva a intentar";
                reply(chatId, stringResponse, null, AWAITING_SESSION);
            }
        }
        else if ("Registrarse".equalsIgnoreCase(message.getText())){
            //flujo de registro
        }
    }
}

