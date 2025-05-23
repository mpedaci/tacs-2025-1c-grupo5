package utn.tacs.grupo5.telegrambot.flow;

import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

/**
 * Interface for managing conversation flows
 */
public interface FlowManager {
    FlowDefinition getFlow(String flowName);
    UserState getNextState(String flowName, UserState currentState, ChatData chatData);
    void registerFlow(String flowName, FlowDefinition flowDefinition);

    UserState getfirstStateInFlow(String flowName);
}
