package utn.tacs.grupo5.telegrambot.flow;

import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

/**
 * Interface for managing conversation flows
 */
public interface FlowManager {

    /**
     * Get a flow definition by name
     */
    FlowDefinition getFlow(String flowName);

    /**
     * Get the next state in a flow based on current state and chat data
     */
    UserState getNextState(String flowName, UserState currentState, ChatData chatData);

    /**
     * Register a new flow definition
     */
    void registerFlow(String flowName, FlowDefinition flowDefinition);

    /**
     * Get the first state of a flow
     */
    UserState getfirstStateInFlow(String flowName);
}