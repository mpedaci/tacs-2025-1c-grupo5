package utn.tacs.grupo5.telegrambot.flow;

import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

import java.util.function.Predicate;

/**
 * Handles conditional transitions between states
 */
@FunctionalInterface
public interface FlowTransition {
    UserState getNextState(ChatData chatData);

    static FlowTransition conditional(Predicate<ChatData> condition, UserState ifTrue, UserState ifFalse) {
        return chatData -> condition.test(chatData) ? ifTrue : ifFalse;
    }

    static FlowTransition direct(UserState nextState) {
        return chatData -> nextState;
    }
}