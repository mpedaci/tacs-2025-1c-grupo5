package utn.tacs.grupo5.telegrambot.flow;

import org.springframework.stereotype.Component;
import utn.tacs.grupo5.telegrambot.ChatData;
import utn.tacs.grupo5.telegrambot.UserState;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static utn.tacs.grupo5.telegrambot.UserState.*;

@Component
public class FlowManagerImpl implements FlowManager {
    private final Map<String, FlowDefinition> flows = new HashMap<>();

    public FlowManagerImpl() {
        initializeFlows();
    }

    private void initializeFlows() {
        // Post creation flow with proper conditional logic
        AdvancedFlowDefinition postFlow = new AdvancedFlowDefinition(List.of(
                CHOOSING_OPTIONS,
                CHOOSING_GAME,
                CHOOSING_CARD,
                SELECTING_OFFERED_CARD,
                CHOOSING_CONDITION,
                CHOOSING_PHOTO_OPTION,
                CHOOSING_PHOTO,
                CHOOSING_VALUE_TYPE,
                CHOOSING_VALUE,
                SELECTING_WANTED_CARDS,
                CHOOSING_DESCRIPTION,
                CREATING_POST
        ));

        // Configure post flow transitions
        configurePostFlow(postFlow);

        flows.put("post", postFlow);

        // Simple flows
        flows.put("register", new AdvancedFlowDefinition(List.of(AWAITING_SESSION, REGISTERING)));
        flows.put("login", new AdvancedFlowDefinition(List.of(AWAITING_SESSION, LOGIN_IN)));
    }

    private void configurePostFlow(AdvancedFlowDefinition postFlow) {
        // Multiple cards selection logic
        postFlow.addConditionalTransition(
                CHOOSING_CARD,
                chatData -> chatData.getCurrentCards() != null && chatData.getCurrentCards().size() > 1,
                SELECTING_OFFERED_CARD,
                CHOOSING_CONDITION
        );

        // Offered card selection with proper loop handling
        postFlow.addConditionalTransition(
                SELECTING_OFFERED_CARD,
                chatData -> chatData.needsMoreCardSelection(),
                SELECTING_OFFERED_CARD,  // Stay in same state if more selection needed
                CHOOSING_CONDITION       // Continue to next step if done
        );

        // Photo collection decision
        postFlow.addConditionalTransition(
                CHOOSING_PHOTO_OPTION,
                chatData -> chatData.shouldCollectPhotos(),
                CHOOSING_PHOTO,
                CHOOSING_VALUE_TYPE
        );

        // Direct transition from photo selection
        postFlow.addDirectTransition(CHOOSING_PHOTO, CHOOSING_VALUE_TYPE);

        // Value type determines if cards are needed
        postFlow.addConditionalTransition(
                CHOOSING_VALUE,
                chatData -> {
                    String helpValue = chatData.getHelpStringValue();
                    return "Cartas".equals(helpValue) || "Ambos".equals(helpValue);
                },
                SELECTING_WANTED_CARDS,
                CHOOSING_DESCRIPTION
        );

        // Wanted cards selection with multiple conditions using priority transitions
        postFlow.addPriorityTransition(
                SELECTING_WANTED_CARDS,
                ChatData::needsMoreCardSelection,
                SELECTING_WANTED_CARDS,
                1  // High priority - stay in selection if more cards needed
        );

        postFlow.addPriorityTransition(
                SELECTING_WANTED_CARDS,
                ChatData::isChoosingAnotherCard,
                CHOOSING_VALUE,
                2  // Medium priority - go back to value selection
        );

    }

    @Override
    public FlowDefinition getFlow(String flowName) {
        return flows.get(flowName);
    }

    @Override
    public UserState getNextState(String flowName, UserState currentState, ChatData chatData) {
        FlowDefinition flow = flows.get(flowName);
        if (flow == null) {
            return CHOOSING_OPTIONS; // Safe fallback
        }

        try {
            UserState nextState = flow.getNextState(currentState, chatData);
            return nextState != null ? nextState : CHOOSING_OPTIONS;
        } catch (Exception e) {
            // Log the error in a real application
            System.err.println("Error in flow transition: " + e.getMessage());
            return CHOOSING_OPTIONS; // Safe fallback
        }
    }

    @Override
    public void registerFlow(String flowName, FlowDefinition flowDefinition) {
        if (flowName == null || flowName.trim().isEmpty()) {
            throw new IllegalArgumentException("Flow name cannot be null or empty");
        }
        if (flowDefinition == null) {
            throw new IllegalArgumentException("Flow definition cannot be null");
        }
        flows.put(flowName, flowDefinition);
    }

    @Override
    public UserState getfirstStateInFlow(String flowName) {
        FlowDefinition flow = flows.get(flowName);
        return flow != null ? flow.getFirstState() : null;
    }

    /**
     * Get all registered flow names
     */
    public java.util.Set<String> getFlowNames() {
        return flows.keySet();
    }

    /**
     * Check if a flow exists
     */
    public boolean hasFlow(String flowName) {
        return flows.containsKey(flowName);
    }

    /**
     * Remove a flow
     */
    public void removeFlow(String flowName) {
        flows.remove(flowName);
    }

    /**
     * Validate all flows
     */
    public boolean validateFlows() {
        for (Map.Entry<String, FlowDefinition> entry : flows.entrySet()) {
            if (!entry.getValue().isValid()) {
                System.err.println("Invalid flow: " + entry.getKey());
                return false;
            }
        }
        return true;
    }
}