package utn.tacs.grupo5.bot.handler;

import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import utn.tacs.grupo5.bot.Chatdata;
import utn.tacs.grupo5.bot.Constants;
import utn.tacs.grupo5.bot.KeyboardFactory;
import utn.tacs.grupo5.bot.UserState;
import utn.tacs.grupo5.bot.handler.exception.BotException;
import utn.tacs.grupo5.controller.exceptions.NotFoundException;
import utn.tacs.grupo5.dto.post.PostInputDto;
import utn.tacs.grupo5.entity.User;
import utn.tacs.grupo5.entity.card.Card;
import utn.tacs.grupo5.entity.post.ConservationStatus;
import utn.tacs.grupo5.service.impl.BotService;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import static utn.tacs.grupo5.bot.Constants.START_TEXT;
import static utn.tacs.grupo5.bot.UserState.*;

@Component
public class ResponseHandler {
    private SilentSender sender;
    private Map<Long, UserState> chatStates;
    private Map<Long, Chatdata> chatData = new HashMap<>();
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
        chatData.put(chatId, new Chatdata(null, null, null));
        reply(chatId,START_TEXT,KeyboardFactory.getStartOption(),AWAITING_SESSION);
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

    public void replyToButtons(long chatId, Message message) {
        UserState state = chatStates.get(chatId);

        if (message.getText().equalsIgnoreCase("/stop")) {
            stopChat(chatId);
        }
        switch (state) {
            case AWAITING_SESSION:
                replyToStartSession(message.getChatId(), message);
                break;
            case LOGIN_IN:
                replyToLogIn(message.getChatId(), message);
                break;
            case REGISTERING:
                replyToRegister(message.getChatId(), message);
                break;
            case CHOOSING_OPTIONS:
                replyToChoosingOptions(message.getChatId(), message);
                break;
            case CHOOSING_GAME:
                replyToChosenGame(message.getChatId(), message);
                break;
            case CHOOSING_CARD:
                replyToChosenCard(message.getChatId(), message);
                break;
            case CHOOSING_CONDITION:
            default: break;
        }
    }

    private void replyToStartSession(long chatId, Message message){
        if ("Log In".equalsIgnoreCase(message.getText())){
            reply(chatId, "ingrese su usuario y contraseña \n->usuario, contraseña", null, LOGIN_IN);
        }
        else if ("Registrarse".equalsIgnoreCase(message.getText())){
            reply(chatId, "Ingrese: \n-> nombre, username, password", null, REGISTERING);
        }
    }

    private void replyToLogIn(long chatId, Message message){
        try {
            Optional<User> user = botService.findUser(message.getText());
            if (user.isPresent()){
                chatData.get(chatId).setUser(user.get().getId());
                String stringResponse = "Bienvenido " + user.get().getName();
                reply(chatId, stringResponse, KeyboardFactory.getCardsOption(), CHOOSING_OPTIONS);
            }
        } catch (BotException e) {
            reply(chatId, e.getMessage(), null, LOGIN_IN);
        }
    }

    private void replyToRegister(long chatId, Message message){
        try {
            botService.registerUser(message.getText());
            reply(chatId, "Registrado con éxito", KeyboardFactory.getCardsOption(), CHOOSING_OPTIONS);
        } catch (BotException e) {
            reply(chatId, e.getMessage(), null, REGISTERING);
        } catch (Exception e) {
            reply(chatId, "Ha ocurrido un error inesperado. Intente nuevamente más tarde.", null, AWAITING_SESSION);
        }
    }

    private void replyToChoosingOptions(long chatId, Message message){
        if ("Publicar Carta".equalsIgnoreCase(message.getText())){
            reply(chatId, "Elija el juego", KeyboardFactory.getGameOption(), CHOOSING_GAME);
        }
    }

    private void replyToChosenGame(long chatId, Message message){
        chatData.get(chatId).setGame(message.getText());
        reply(chatId, "Elija la carta (el nombre debe ser exacto)", null, CHOOSING_CARD);
    }

    private void replyToChosenCard(long chatId, Message message){
        try {
            Card card = botService.findCard(chatData.get(chatId).getGame(),message.getText());
            chatData.get(chatId).getPostInputDto().setCardId(card.getId());
            reply(chatId, "Elija el estado de conservacion de la carta", KeyboardFactory.getCardConditionOption(), CHOOSING_CONDITION);
        } catch (BotException e) {
            reply(chatId, e.getMessage(), null, CHOOSING_CARD);
        }
    }

    private void replyToChosenCondition(long chatId, Message message){
        try {
            chatData.get(chatId).getPostInputDto().setConservationStatus(ConservationStatus.valueOf(message.getText()));
            reply(chatId, "Ingrese el valor estimado de la carta", null, CHOOSING_VALUE);
        } catch (IllegalArgumentException e) {
            reply(chatId, "Estado de conservación inválido. Intente nuevamente.", null, CHOOSING_CONDITION);
        }
    }

    private UUID getUserFromChatId(long chatId) {
        return chatData.get(chatId).getUser();
    }
}

