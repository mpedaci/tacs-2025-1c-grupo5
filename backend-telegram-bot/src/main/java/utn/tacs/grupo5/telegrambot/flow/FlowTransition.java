package utn.tacs.grupo5.telegrambot.flow;

import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

import java.util.function.Predicate;

/**
 * Represents a transition between states in a flow
 * Enhanced with better condition evaluation
 */
public interface FlowTransition {
    UserState getNextState(ChatData chatData);

    /**
     * Creates a direct transition to a specific state
     */
    static FlowTransition direct(UserState targetState) {
        return new DirectTransition(targetState);
    }

    /**
     * Creates a conditional transition
     */
    static FlowTransition conditional(Predicate<ChatData> condition, UserState trueState, UserState falseState) {
        return new ConditionalTransition(condition, trueState, falseState);
    }

    /**
     * Creates a predicate-based transition that only applies when condition is true
     */
    static FlowTransition when(Predicate<ChatData> condition, UserState targetState) {
        return new PredicateTransition(condition, targetState);
    }

    /**
     * Direct transition implementation
     */
    class DirectTransition implements FlowTransition {
        private final UserState targetState;

        public DirectTransition(UserState targetState) {
            this.targetState = targetState;
        }

        @Override
        public UserState getNextState(ChatData chatData) {
            return targetState;
        }

        public UserState getTargetState() {
            return targetState;
        }
    }

    /**
     * Conditional transition implementation - enhanced with better access methods
     */
    class ConditionalTransition implements FlowTransition {
        private final Predicate<ChatData> condition;
        private final UserState trueState;
        private final UserState falseState;

        public ConditionalTransition(Predicate<ChatData> condition, UserState trueState, UserState falseState) {
            this.condition = condition;
            this.trueState = trueState;
            this.falseState = falseState;
        }

        @Override
        public UserState getNextState(ChatData chatData) {
            return condition.test(chatData) ? trueState : falseState;
        }

        public boolean evaluateCondition(ChatData chatData) {
            return condition.test(chatData);
        }

        public UserState getTrueState() {
            return trueState;
        }

        public UserState getFalseState() {
            return falseState;
        }

        public Predicate<ChatData> getCondition() {
            return condition;
        }
    }

    /**
     * Predicate-based transition that only applies when condition is met
     * Returns null if condition is not met (allowing fallback to next transition)
     */
    class PredicateTransition implements FlowTransition {
        private final Predicate<ChatData> condition;
        private final UserState targetState;

        public PredicateTransition(Predicate<ChatData> condition, UserState targetState) {
            this.condition = condition;
            this.targetState = targetState;
        }

        @Override
        public UserState getNextState(ChatData chatData) {
            return condition.test(chatData) ? targetState : null;
        }

        public boolean applies(ChatData chatData) {
            return condition.test(chatData);
        }

        public UserState getTargetState() {
            return targetState;
        }

        public Predicate<ChatData> getCondition() {
            return condition;
        }
    }
}