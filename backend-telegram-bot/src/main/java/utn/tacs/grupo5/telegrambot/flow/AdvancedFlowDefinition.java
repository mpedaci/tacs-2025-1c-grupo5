package utn.tacs.grupo5.telegrambot.flow;

import utn.tacs.grupo5.telegrambot.telegram.ChatData;
import utn.tacs.grupo5.telegrambot.telegram.UserState;

import java.util.*;
import java.util.function.Predicate;

/**
 * Advanced FlowDefinition with support for priority-based transitions
 * Fixed implementation with proper transition evaluation
 */
public class AdvancedFlowDefinition extends FlowDefinition {
    private final Map<UserState, List<PriorityTransition>> priorityTransitions;

    public AdvancedFlowDefinition(List<UserState> states) {
        super(states);
        this.priorityTransitions = new HashMap<>();
    }

    /**
     * Add transition with priority (lower number = higher priority)
     */
    public AdvancedFlowDefinition addPriorityTransition(UserState from,
                                                        Predicate<ChatData> condition,
                                                        UserState to,
                                                        int priority) {
        List<PriorityTransition> stateTransitions =
                priorityTransitions.computeIfAbsent(from, k -> new ArrayList<>());

        stateTransitions.add(new PriorityTransition(condition, to, priority));

        // Sort by priority after adding
        stateTransitions.sort(Comparator.comparingInt(t -> t.priority));

        return this;
    }

    /**
     * Add multiple priority transitions
     */
    public AdvancedFlowDefinition addPriorityTransitions(UserState from, PriorityTransition... transitions) {
        List<PriorityTransition> stateTransitions =
                priorityTransitions.computeIfAbsent(from, k -> new ArrayList<>());

        stateTransitions.addAll(Arrays.asList(transitions));
        stateTransitions.sort(Comparator.comparingInt(t -> t.priority));

        return this;
    }

    @Override
    public UserState getNextState(UserState current, ChatData chatData) {
        // First check priority transitions (highest priority first)
        List<PriorityTransition> stateTransitions = priorityTransitions.get(current);
        if (stateTransitions != null) {
            for (PriorityTransition transition : stateTransitions) {
                if (transition.condition.test(chatData)) {
                    return transition.targetState;
                }
            }
        }

        // Fall back to regular transitions
        return super.getNextState(current, chatData);
    }

    /**
     * Get priority transitions for debugging
     */
    public List<PriorityTransition> getPriorityTransitions(UserState state) {
        return priorityTransitions.getOrDefault(state, new ArrayList<>());
    }

    /**
     * Check if state has priority transitions
     */
    public boolean hasPriorityTransitions(UserState state) {
        List<PriorityTransition> transitions = priorityTransitions.get(state);
        return transitions != null && !transitions.isEmpty();
    }

    /**
     * Priority transition class
     */
    public static class PriorityTransition {
        final Predicate<ChatData> condition;
        final UserState targetState;
        final int priority;

        public PriorityTransition(Predicate<ChatData> condition, UserState targetState, int priority) {
            this.condition = condition;
            this.targetState = targetState;
            this.priority = priority;
        }

        public static PriorityTransition create(Predicate<ChatData> condition, UserState targetState, int priority) {
            return new PriorityTransition(condition, targetState, priority);
        }

        // Convenience methods for common priorities
        public static PriorityTransition high(Predicate<ChatData> condition, UserState targetState) {
            return new PriorityTransition(condition, targetState, 1);
        }

        public static PriorityTransition medium(Predicate<ChatData> condition, UserState targetState) {
            return new PriorityTransition(condition, targetState, 5);
        }

        public static PriorityTransition low(Predicate<ChatData> condition, UserState targetState) {
            return new PriorityTransition(condition, targetState, 10);
        }

        // Getters for inspection
        public Predicate<ChatData> getCondition() {
            return condition;
        }

        public UserState getTargetState() {
            return targetState;
        }

        public int getPriority() {
            return priority;
        }

        public boolean applies(ChatData chatData) {
            return condition.test(chatData);
        }
    }
}