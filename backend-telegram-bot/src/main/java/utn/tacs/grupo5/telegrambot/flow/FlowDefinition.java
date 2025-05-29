package utn.tacs.grupo5.telegrambot.flow;


import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.telegram.UserState;

import java.util.*;
import java.util.function.Predicate;

import static utn.tacs.grupo5.telegrambot.telegram.UserState.CHOOSING_OPTIONS;

/**
 * Defines a conversation flow with its states and transitions
 * Enhanced to support multiple transitions per state with proper evaluation
 */
public class FlowDefinition {
    private final List<UserState> states;
    private final Map<UserState, List<FlowTransition>> transitions;

    public FlowDefinition(List<UserState> states) {
        this.states = new ArrayList<>(states);
        this.transitions = new HashMap<>();
    }

    /**
     * Add a single transition
     */
    public FlowDefinition addTransition(UserState from, FlowTransition transition) {
        transitions.computeIfAbsent(from, k -> new ArrayList<>()).add(transition);
        return this;
    }

    /**
     * Add multiple transitions for a state
     */
    public FlowDefinition addTransitions(UserState from, FlowTransition... flowTransitions) {
        List<FlowTransition> stateTransitions = transitions.computeIfAbsent(from, k -> new ArrayList<>());
        stateTransitions.addAll(Arrays.asList(flowTransitions));
        return this;
    }

    /**
     * Add a conditional transition (convenience method)
     */
    public FlowDefinition addConditionalTransition(UserState from,
                                                   Predicate<ChatData> condition,
                                                   UserState trueState,
                                                   UserState falseState) {
        return addTransition(from, FlowTransition.conditional(condition, trueState, falseState));
    }

    /**
     * Add a direct transition (convenience method)
     */
    public FlowDefinition addDirectTransition(UserState from, UserState to) {
        return addTransition(from, FlowTransition.direct(to));
    }

    /**
     * Get next state by evaluating transitions in order
     * Fixed logic for proper transition evaluation
     */
    public UserState getNextState(UserState current, ChatData chatData) {
        List<FlowTransition> stateTransitions = transitions.get(current);

        if (stateTransitions != null && !stateTransitions.isEmpty()) {
            // Evaluate transitions in order
            for (FlowTransition transition : stateTransitions) {
                if (transition instanceof FlowTransition.DirectTransition) {
                    // Direct transitions always apply
                    return transition.getNextState(chatData);
                } else if (transition instanceof FlowTransition.ConditionalTransition) {
                    // For conditional transitions, we need to check if the condition is met
                    FlowTransition.ConditionalTransition condTransition =
                            (FlowTransition.ConditionalTransition) transition;

                    // If condition is true, use the true state
                    if (condTransition.evaluateCondition(chatData)) {
                        return condTransition.getTrueState();
                    } else {
                        // If condition is false, use the false state
                        return condTransition.getFalseState();
                    }
                } else {
                    // Generic transition evaluation
                    return transition.getNextState(chatData);
                }
            }
        }

        // Default sequential behavior - move to next state in the list
        return getNextSequentialState(current);
    }

    /**
     * Get the next sequential state in the flow
     */
    private UserState getNextSequentialState(UserState current) {
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

    /**
     * Get all transitions for a state (for debugging/inspection)
     */
    public List<FlowTransition> getTransitions(UserState state) {
        return transitions.getOrDefault(state, new ArrayList<>());
    }

    /**
     * Check if a state has custom transitions
     */
    public boolean hasTransitions(UserState state) {
        List<FlowTransition> stateTransitions = transitions.get(state);
        return stateTransitions != null && !stateTransitions.isEmpty();
    }

    /**
     * Validate the flow definition
     */
    public boolean isValid() {
        // Check if all states are valid
        if (states.isEmpty()) {
            return false;
        }

        // Check if transitions reference valid states
        for (Map.Entry<UserState, List<FlowTransition>> entry : transitions.entrySet()) {
            UserState fromState = entry.getKey();
            if (!states.contains(fromState)) {
                return false;
            }
            // Additional validation could be added here for target states
        }

        return true;
    }
}