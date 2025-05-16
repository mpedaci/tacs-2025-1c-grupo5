package utn.tacs.grupo5.bot.handler;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.GetFile;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utn.tacs.grupo5.bot.Chatdata;
import utn.tacs.grupo5.bot.Constants;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.command.StateCommand;
import utn.tacs.grupo5.bot.handler.command.StateCommandFactory;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.service.impl.BotService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utn.tacs.grupo5.bot.Constants.START_TEXT;
import static utn.tacs.grupo5.bot.UserState.*;

@Component
public class ResponseHandler {
    @Getter
    private SilentSender sender;
    private AbsSender absSender;
    // Getter and setter methods for the state commands to access
    @Getter
    private Map<Long, UserState> chatStates;
    @Getter
    private Map<Long, Chatdata> chatData = new HashMap<>();
    @Getter
    private BotService botService;
    private StateCommandFactory commandFactory;

    public ResponseHandler(BotService botService, StateCommandFactory commandFactory) {
        this.botService = botService;
        this.commandFactory = commandFactory;
    }

    public void init(SilentSender sender, DBContext db, AbsSender absSender) {
        this.sender = sender;
        this.absSender = absSender;
        chatStates = db.getMap(Constants.CHAT_STATES);
        //chatData = db.getMap(Constants.CHAT_DATA); //TODO hacer persistente el estado de los chats
    }

    public boolean userIsActive(Long chatId) {
        return chatStates.containsKey(chatId);
    }

    public void reply(long chatId, String text, ReplyKeyboard replyKeyboard, UserState userState) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        if (replyKeyboard != null) {
            message.setReplyMarkup(replyKeyboard);
        }
        sender.execute(message);
        this.chatStates.put(chatId, userState); //TODO refactor
    }

    public void reply(long chatId, String text, ReplyKeyboard replyKeyboard) {
        SendMessage message = new SendMessage();
        message.setChatId(chatId);
        message.setText(text);
        if (replyKeyboard != null) {
            message.setReplyMarkup(replyKeyboard);
        }
        sender.execute(message);
    }

    public void enviarFoto(Long chatId, String caption, String imageUrl) {
        SendPhoto sendPhoto = new SendPhoto();
        sendPhoto.setChatId(chatId.toString());
        sendPhoto.setCaption(caption);
        sendPhoto.setPhoto(new InputFile(imageUrl)); // Puede ser URL o archivo

        try {
            absSender.execute(sendPhoto);
        } catch (TelegramApiException e) {
            e.printStackTrace();
        }
    }


    public void replyToStart(long chatId) {
        chatData.put(chatId, new Chatdata());
        StateCommand command = commandFactory.getCommand(AWAITING_SESSION);
        command.onEnter(chatId, this);

    }

    private void stopChat(long chatId) {
        SendMessage sendMessage = new SendMessage();
        sendMessage.setChatId(chatId);
        sendMessage.setText("Saliendo");
        chatStates.remove(chatId);
        chatData.remove(chatId);
        sendMessage.setReplyMarkup(new ReplyKeyboardRemove(true));
        sender.execute(sendMessage);
    }

    public void replyToChat(long chatId, Message message) {
        if (message.getText().equalsIgnoreCase("/stop")) {
            stopChat(chatId);
            return;
        }

        UserState state = chatStates.get(chatId);
        StateCommand command = commandFactory.getCommand(state);
        command.execute(chatId, message, this);
        if(!chatStates.get(chatId).equals(CHOOSING_PHOTO)) {
            UserState nextUserState = commandFactory.nextUserStateInFlow(chatData.get(chatId).getFlow(), state);
            this.chatStates.put(chatId, nextUserState);
            StateCommand nextCommand = commandFactory.getCommand(nextUserState);
            nextCommand.onEnter(chatId, this);
        }
    }
    public void replyToPhoto(long chatId, List<PhotoSize> photos) {
        UserState state = chatStates.get(chatId);
        StateCommand command = commandFactory.getCommand(state);
        command.handlePhoto(chatId, photos, this);



        chatStates.put(chatId, CHOOSING_PHOTO_OPTION);//TODO refactor

        UserState nextUserState = commandFactory.nextUserStateInFlow(chatData.get(chatId).getFlow(), chatStates.get(chatId));
        StateCommand nextCommand = commandFactory.getCommand(nextUserState);
        nextCommand.onEnter(chatId, this);
        chatStates.put(chatId, nextUserState);
    }
}