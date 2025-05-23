package utn.tacs.grupo5.telegrambot.handler;

import lombok.Getter;
import org.springframework.stereotype.Component;
import org.telegram.abilitybots.api.db.DBContext;
import org.telegram.abilitybots.api.sender.SilentSender;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.methods.send.SendPhoto;
import org.telegram.telegrambots.meta.api.objects.InputFile;
import org.telegram.telegrambots.meta.api.objects.Message;
import org.telegram.telegrambots.meta.api.objects.PhotoSize;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboard;
import org.telegram.telegrambots.meta.api.objects.replykeyboard.ReplyKeyboardRemove;
import org.telegram.telegrambots.meta.bots.AbsSender;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.Constants;
import utn.tacs.grupo5.telegrambot.UserState;
import utn.tacs.grupo5.telegrambot.command.StateCommand;
import utn.tacs.grupo5.telegrambot.command.StateCommandFactory;
import utn.tacs.grupo5.telegrambot.exception.BotException;
import utn.tacs.grupo5.telegrambot.flow.FlowManager;
import utn.tacs.grupo5.telegrambot.service.impl.BotService;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utn.tacs.grupo5.telegrambot.UserState.*;


@Component
public class ResponseHandler {
    @Getter
    private SilentSender sender;
    private AbsSender absSender;
    // Getter and setter methods for the state commands to access
    @Getter
    private Map<Long, UserState> chatStates;
    @Getter
    private Map<Long, ChatData> chatData = new HashMap<>();
    @Getter
    private BotService botService;
    private StateCommandFactory commandFactory;
    private final FlowManager flowManager;

    public ResponseHandler(BotService botService, StateCommandFactory commandFactory, FlowManager flowManager) {
        this.botService = botService;
        this.commandFactory = commandFactory;
        this.flowManager = flowManager;
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

    public void replyWithPhoto(Long chatId, String caption, String imageUrl) {
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
        chatData.put(chatId, new ChatData());
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
        if (message.getText().equalsIgnoreCase("/cancel")) {
            UserState firstState = flowManager.getfirstStateInFlow(chatData.get(chatId).getFlow());
            reply(chatId, "Cancelando", null);
            commandFactory.getCommand(firstState).onEnter(chatId, this);
            chatData.get(chatId).clearFlowData();
            return;
        }

        UserState currentState = chatStates.get(chatId);
        StateCommand command = commandFactory.getCommand(currentState);
        ChatData currentChatData = chatData.get(chatId);

        try {
            // Execute the command first
            command.execute(chatId, message, this);

            // Handle state transitions
            if (currentState == CHOOSING_PHOTO) {
                String messageText = message.getText();

                // Only transition if user said "continuar" or "omitir"
                if (messageText != null &&
                        (messageText.equalsIgnoreCase("continuar") || messageText.equalsIgnoreCase("omitir"))) {

                    // Force transition to next state
                    transitionToNextState(chatId, currentState, currentChatData);
                }
                // If it's not "continuar" or "omitir", stay in the same state (user is still sending photos or gave instructions)

            } else {
                // Normal state transition processing for all other states
                transitionToNextState(chatId, currentState, currentChatData);
            }

        } catch (BotException e) {
            handleBotException(chatId, e, currentChatData);
        } catch (Exception e) {
            handleUnexpectedException(chatId, e, currentChatData);
        }
    }

    private void transitionToNextState(long chatId, UserState currentState, ChatData chatData) {
        String flowName = chatData.getFlow();
        UserState nextState = flowManager.getNextState(flowName, currentState, chatData);

        if (nextState != currentState) {
            chatStates.put(chatId, nextState);
            StateCommand nextCommand = commandFactory.getCommand(nextState);
            nextCommand.onEnter(chatId, this);
        }
    }

    private void handleBotException(long chatId, BotException e, ChatData chatData) {
        reply(chatId, e.getMessage(), null);
        reply(chatId, "Intente nuevamente", chatData.getReplyKeyboard());
    }

    private void handleUnexpectedException(long chatId, Exception e, ChatData chatData) {
        //logger.error("Unexpected error in chat {}: {}", chatId, e.getMessage(), e);
        reply(chatId, "Ocurrió un error inesperado. Por favor intente nuevamente.", null);
        reply(chatId, "Si el problema persiste, use /stop para reiniciar.", chatData.getReplyKeyboard());
    }

    public void replyToPhoto(long chatId, List<PhotoSize> photos) {
        UserState currentState = chatStates.get(chatId);

        if (currentState == null) {
            reply(chatId, "⚠️ Sesión no iniciada. Use /start para comenzar.", null);
            return;
        }

        // Only handle photos in the photo collection state
        if (currentState.equals(CHOOSING_PHOTO)) {
            StateCommand command = commandFactory.getCommand(CHOOSING_PHOTO);

            try {
                command.handlePhoto(chatId, photos, this, absSender);

                // Don't auto-transition - let the user decide when to continue
                // The command itself will provide feedback about the photo being saved

            } catch (Exception e) {
                ChatData currentChatData = chatData.get(chatId);
                handleUnexpectedException(chatId, e, currentChatData);
            }

        } else {
            // User sent photo in wrong state
            reply(chatId, "⚠️ No se esperan fotos en este momento. " +
                    "Siga las instrucciones en pantalla.", null);
        }
    }
}