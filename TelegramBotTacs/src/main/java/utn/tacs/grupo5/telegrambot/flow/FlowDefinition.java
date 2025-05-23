package utn.tacs.grupo5.telegrambot.flow;

import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utn.tacs.grupo5.telegrambot.UserState.CHOOSING_OPTIONS;

/**
 * Defines a conversation flow with its states and transitions
 */
public class FlowDefinition {
    private final List<UserState> states;
    private final Map<UserState, FlowTransition> transitions;

    public FlowDefinition(List<UserState> states) {
        this.states = new ArrayList<>(states);
        this.transitions = new HashMap<>();
    }

    public FlowDefinition addTransition(UserState from, FlowTransition transition) {
        transitions.put(from, transition);
        return this;
    }

    public UserState getNextState(UserState current, ChatData chatData) {
        FlowTransition transition = transitions.get(current);
        if (transition != null) {
            return transition.getNextState(chatData);
        }

        int currentIndex = states.indexOf(current);
        if (currentIndex >= 0 && currentIndex < states.size() - 1) {
            return states.get(currentIndex + 1);
        }

        return CHOOSING_OPTIONS; // Default fallback
    }

    public UserState getFirstState() {
        return states.isEmpty() ? null : states.get(0);
    }

    public List<UserState> getStates() {
        return new ArrayList<>(states);
    }
}